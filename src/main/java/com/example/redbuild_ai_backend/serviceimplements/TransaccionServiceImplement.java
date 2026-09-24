package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.dtos.TransaccionUsuarioDTO;
import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransaccionServiceImplement implements ITransaccionService {

    private final ITransaccionRepository tR;

    public TransaccionServiceImplement(ITransaccionRepository tR) {
        this.tR = tR;
    }

    @Override
    public void insert(Transaccion t) {
        tR.save(t);
    }

    @Override
    public List<Transaccion> list() {
        return tR.findAll();
    }

    @Override
    public List<TransaccionUsuarioDTO> findTransactionsByUserId(Long userId) {
        return tR.findTransactionsByUserId(userId).stream().map(row -> {
            TransaccionUsuarioDTO dto = new TransaccionUsuarioDTO();
            dto.setIdTransaccion(((Number) row[0]).longValue());
            dto.setTypeTransaction((String) row[1]);
            dto.setAmountTransaction(((Number) row[2]).doubleValue());
            dto.setDescriptionTransaction((String) row[3]);
            dto.setPaymentMethod((String) row[4]);

            Object fecha = row[5];
            if (fecha instanceof Timestamp timestamp) {
                dto.setDateRegisterTransaction(timestamp.toLocalDateTime());
            } else if (fecha instanceof LocalDateTime localDateTime) {
                dto.setDateRegisterTransaction(localDateTime);
            }

            dto.setStatusTransaction((Boolean) row[6]);
            dto.setIdUser(((Number) row[7]).longValue());
            dto.setNameUser((String) row[8]);
            dto.setEmailUser((String) row[9]);
            return dto;
        }).toList();
    }

    @Override
    public void delete(Long id) {
        tR.deleteById(id);
    }

    @Override
    public Optional<Transaccion> listId(Long id) {
        return tR.findById(id);
    }

    @Override
    public void update(Transaccion transaccion) {
        tR.save(transaccion);
    }
}
