package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Transaccion;

import java.util.List;
import java.util.Optional;

public interface ITransaccionService {

    void insert(Transaccion t);
    List<Transaccion> list();
    void delete(Long id);
    Optional<Transaccion> listId(Long id);
    void update(Transaccion transaccion);
}
