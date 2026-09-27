package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Location;
import com.example.redbuild_ai_backend.repositories.ILocationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ILocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImplement implements ILocationService {
    private final ILocationRepository lR;

    public LocationServiceImplement(ILocationRepository lR) {
        this.lR = lR;
    }

    @Override
    public void insert(Location l) {
        lR.save(l);
    }

    @Override
    public List<Location> list() {
        return lR.findAll();
    }

    @Override
    public void delete(Long id) {
        lR.deleteById(id);
    }

    @Override
    public Optional<Location> listId(Long id) {
        return lR.findById(id);
    }

    @Override
    public void update(Location loc) {
        lR.save(loc);
    }

    @Override
    public List<Location> findByDepartment(String department){
        return lR.findByDepartment(department);
    }
}
