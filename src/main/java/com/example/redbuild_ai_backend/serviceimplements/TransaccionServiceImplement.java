package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Transaccion;
import com.example.redbuild_ai_backend.repositories.ITransaccionRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ITransaccionService;
import org.springframework.stereotype.Service;

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
