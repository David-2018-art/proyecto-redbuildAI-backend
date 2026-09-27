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

            dto.setIdTransaccion(convertToLong(row[0]));
            dto.setTypeTransaction(convertToString(row[1]));
            dto.setAmountTransaction(convertToDouble(row[2]));
            dto.setDescriptionTransaction(convertToString(row[3]));
            dto.setPaymentMethod(convertToString(row[4]));

            Object fecha = row[5];
            if (fecha instanceof Timestamp timestamp) {
                dto.setDateRegisterTransaction(timestamp.toLocalDateTime());
            } else if (fecha instanceof LocalDateTime localDateTime) {
                dto.setDateRegisterTransaction(localDateTime);
            } else if (fecha instanceof java.sql.Date sqlDate) {
                dto.setDateRegisterTransaction(sqlDate.toLocalDate().atStartOfDay());
            } else if (fecha != null) {
                dto.setDateRegisterTransaction(LocalDateTime.parse(fecha.toString()));
            }

            dto.setStatusTransaction(convertToBoolean(row[6]));
            dto.setIdUser(convertToLong(row[7]));
            dto.setNameUser(convertToString(row[8]));
            dto.setEmailUser(convertToString(row[9]));
            return dto;
        }).toList();
    }

    private Long convertToLong(Object value) {
        if (value == null) return null;
        if (value instanceof Number number) return number.longValue();
        return Long.parseLong(value.toString());
    }

    private Double convertToDouble(Object value) {
        if (value == null) return 0.0;
        if (value instanceof Number number) return number.doubleValue();
        return Double.parseDouble(value.toString());
    }

    private Boolean convertToBoolean(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean bool) return bool;
        if (value instanceof Number number) return number.intValue() != 0;
        return Boolean.parseBoolean(value.toString());
    }

    private String convertToString(Object value) {
        return value == null ? null : value.toString();
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
