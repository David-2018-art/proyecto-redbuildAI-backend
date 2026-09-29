package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.TransaccionUsuarioDTO;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class TransaccionServiceImplement
        implements ITransaccionService {

    private static final Set<String> ESTADOS_VALIDOS = Set.of(
            "Solicitada",
            "Reservada",
            "Completada",
            "Cancelada",
            "Rechazada"
    );

    private final ITransaccionRepository tR;

    public TransaccionServiceImplement(ITransaccionRepository tR) {
        this.tR = tR;
    }

    @Override
    @Transactional
    public void insert(Transaccion transaccion) {

        if (transaccion.getIdTransaccion() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No envíes un ID para registrar una transacción"
            );
        }

        validarDatos(transaccion);

        if (!"Solicitada".equals(transaccion.getStatusTransaction())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Una transacción nueva debe tener estado Solicitada"
            );
        }

        tR.saveAndFlush(transaccion);
    }

    @Override
    public List<Transaccion> list() {
        return tR.findAll();
    }

    @Override
    public Optional<Transaccion> listId(Long id) {
        return tR.findById(id);
    }

    @Override
    public List<TransaccionUsuarioDTO> findTransactionsByUserId(
            Long userId) {

        return tR.findTransactionsByUserId(userId)
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Override
    @Transactional
    public void update(Transaccion transaccion) {

        Long id = transaccion.getIdTransaccion();

        if (id == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El ID de la transacción es obligatorio"
            );
        }

        if (!tR.existsById(id)) {
            throw new ResourceNotFoundException(
                    "No existe la transacción con ID: " + id
            );
        }

        validarDatos(transaccion);

        tR.saveAndFlush(transaccion);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Transaccion transaccion = tR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la transacción con ID: " + id
                ));

        tR.delete(transaccion);
        tR.flush();
    }

    private void validarDatos(Transaccion transaccion) {

        String tipo = transaccion.getTypeTransaction();

        if (!"Venta".equals(tipo) && !"Donacion".equals(tipo)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El tipo de operación debe ser Venta o Donacion"
            );
        }

        BigDecimal cantidad = transaccion.getQuantityTransaction();
        BigDecimal precio = transaccion.getAgreedUnitPrice();

        if (cantidad == null
                || cantidad.compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor que cero"
            );
        }

        if (precio == null
                || precio.compareTo(BigDecimal.ZERO) < 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El precio es obligatorio y no puede ser negativo"
            );
        }

        if ("Donacion".equals(tipo)
                && precio.compareTo(BigDecimal.ZERO) != 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "En una donación el precio debe ser cero"
            );
        }

        if ("Venta".equals(tipo)
                && precio.compareTo(BigDecimal.ZERO) <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "En una venta el precio debe ser mayor que cero"
            );
        }

        String estado = transaccion.getStatusTransaction();

        if (estado == null || !ESTADOS_VALIDOS.contains(estado)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El estado de la transacción no es válido"
            );
        }

        if (transaccion.getPublication() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La publicación es obligatoria"
            );
        }

        if (transaccion.getUser() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El usuario adquirente es obligatorio"
            );
        }

        if (!Objects.equals(
                tipo,
                transaccion.getPublication().getOperationType())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El tipo de operación debe coincidir "
                            + "con el de la publicación"
            );
        }
    }

    private TransaccionUsuarioDTO convertirDTO(
            Transaccion transaccion) {

        TransaccionUsuarioDTO dto = new TransaccionUsuarioDTO();

        dto.setIdTransaccion(transaccion.getIdTransaccion());
        dto.setTypeTransaction(transaccion.getTypeTransaction());
        dto.setQuantityTransaction(
                transaccion.getQuantityTransaction()
        );
        dto.setAgreedUnitPrice(transaccion.getAgreedUnitPrice());
        dto.setAmountTransaction(transaccion.getAmountTransaction());
        dto.setDescriptionTransaction(
                transaccion.getDescriptionTransaction()
        );
        dto.setDateRegisterTransaction(
                transaccion.getDateRegisterTransaction()
        );
        dto.setReservationDate(transaccion.getReservationDate());
        dto.setClosingDate(transaccion.getClosingDate());
        dto.setStatusTransaction(transaccion.getStatusTransaction());

        dto.setIdPublication(
                transaccion.getPublication().getId()
        );
        dto.setIdUser(
                transaccion.getUser().getIdUser()
        );
        dto.setNameUser(
                transaccion.getUser().getNameUser()
        );
        dto.setEmailUser(
                transaccion.getUser().getEmailUser()
        );

        return dto;
    }
}