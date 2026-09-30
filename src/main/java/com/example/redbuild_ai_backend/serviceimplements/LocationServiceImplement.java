package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Location;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.ILocationRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ILocationService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImplement implements ILocationService {

    private final ILocationRepository lR;
    private final IPublicationRepository publicationRepository;

    public LocationServiceImplement(
            ILocationRepository lR,
            IPublicationRepository publicationRepository) {

        this.lR = lR;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public void insert(Location location) {
        lR.save(location);
    }

    @Override
    public List<Location> list() {
        return lR.findAll();
    }

    @Override
    public Optional<Location> listId(Long id) {
        return lR.findById(id);
    }

    @Override
    public void update(Location location) {
        lR.save(location);
    }

    @Override
    public List<Location> findByDepartment(String department) {
        return lR.findByDepartment(department);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Location location = lR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la ubicación con ID: " + id
                ));

        if (publicationRepository.existsByLocation_IdLocation(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la ubicación porque "
                            + "tiene publicaciones asociadas"
            );
        }

        try {
            lR.delete(location);
            lR.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la ubicación porque "
                            + "tiene registros asociados",
                    exception
            );
        }
    }
}