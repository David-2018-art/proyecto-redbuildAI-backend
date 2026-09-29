package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.dtos.TransaccionUsuarioDTO;
import com.example.redbuild_ai_backend.entities.Transaccion;

import java.util.List;
import java.util.Optional;

public interface ITransaccionService {

    void insert(Transaccion transaccion);

    List<Transaccion> list();

    Optional<Transaccion> listId(Long id);

    List<TransaccionUsuarioDTO> findTransactionsByUserId(Long userId);

    void update(Transaccion transaccion);

    void delete(Long id);
}