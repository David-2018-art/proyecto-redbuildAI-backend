package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.repositories.IProductRepository;
import com.example.redbuild_ai_backend.repositories.IPublicationRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements IProductService {

    private final IProductRepository pR;
    private final IPublicationRepository publicationRepository;

    public ProductServiceImplement(
            IProductRepository pR,
            IPublicationRepository publicationRepository) {

        this.pR = pR;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public void insert(Product product) {
        pR.save(product);
    }

    @Override
    public List<Product> list() {
        return pR.findAll();
    }

    @Override
    public Optional<Product> listId(Long id) {
        return pR.findById(id);
    }

    @Override
    public void update(Product product) {
        pR.save(product);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Product product = pR.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe el producto con ID: " + id
                ));

        long cantidadPublicaciones =
                publicationRepository.countByProduct_IdProduct(id);

        if (cantidadPublicaciones > 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el producto porque "
                            + "tiene publicaciones asociadas"
            );
        }

        try {
            pR.delete(product);
            pR.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar el producto porque "
                            + "tiene registros asociados",
                    exception
            );
        }
    }
}