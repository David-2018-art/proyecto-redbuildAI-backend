package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Location;

import java.util.List;
import java.util.Optional;

public interface ILocationService {
    public void insert(Location l);
    public List<Location> list();
    public void delete(Long id);
    public Optional<Location> listId(Long id);
    public void update(Location loc);

    public List<Location> findByDepartment(String department);
}
