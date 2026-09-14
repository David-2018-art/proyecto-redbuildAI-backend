package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Resena;

import java.util.List;
import java.util.Optional;

public interface IResenaService {

    void insert(Resena r);
    List<Resena> list();
    void delete(Long id);
    Optional<Resena> listId(Long id);
    void update(Resena resena);
}
