package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.EmpresaResumenDTO;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmpresaReporteService {
    private final IUserRepository userRepository;
    private final ITransaccionRepository transaccionRepository;

    public EmpresaReporteService(
            IUserRepository userRepository,
            ITransaccionRepository transaccionRepository) {

        this.userRepository = userRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional(readOnly = true)
    public EmpresaResumenDTO obtenerMiResumen(String email) {

        User empresa = userRepository.findByEmailUser(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "La cuenta autenticada no existe"
                ));

        if (!"Activo".equalsIgnoreCase(empresa.getStatusUser())
                || empresa.getRole() == null
                || !"Activo".equalsIgnoreCase(
                empresa.getRole().getStatusRole())
                || !"Empresa".equals(
                empresa.getRole().getNameRole())) {

            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Este reporte es exclusivo para cuentas Empresa activas"
            );
        }

        List<Object[]> filas = transaccionRepository
                .resumenPorEmpresa(empresa.getIdUser());

        EmpresaResumenDTO resumen = new EmpresaResumenDTO();

        for (Object[] fila : filas) {

            String tipo = (String) fila[0];
            long cantidad = ((Number) fila[1]).longValue();

            BigDecimal monto = fila[2] == null
                    ? BigDecimal.ZERO
                    : (BigDecimal) fila[2];

            if ("Venta".equals(tipo)) {
                resumen.setVentasCompletadas(cantidad);
                resumen.setIngresosVentas(monto);
            } else if ("Donacion".equals(tipo)) {
                resumen.setDonacionesCompletadas(cantidad);
            }
        }

        return resumen;
    }
}
