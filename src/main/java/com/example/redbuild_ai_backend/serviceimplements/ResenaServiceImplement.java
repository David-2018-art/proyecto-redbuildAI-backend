package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Resena;
import com.example.redbuild_ai_backend.repositories.IResenaRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IResenaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaServiceImplement implements IResenaService {

    private final IResenaRepository rR;

    public ResenaServiceImplement(IResenaRepository rR) {
        this.rR = rR;
    }

    @Override
    public void insert(Resena r) {
        rR.save(r);
    }

    @Override
    public List<Resena> list() {
        return rR.findAll();
    }

    @Override
    public List<Resena> findByUserId(Long userId) {
        return rR.findByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        rR.deleteById(id);
    }

    @Override
    public Optional<Resena> listId(Long id) {
        return rR.findById(id);
    }

    @Override
    public void update(Resena resena) {
        rR.save(resena);
    }
}
