package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Product;
import com.example.redbuild_ai_backend.repositories.IProductRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplement implements IProductService {
    private final IProductRepository pR;

    public ProductServiceImplement(IProductRepository pR) {
        this.pR = pR;
    }


    @Override
    public void insert(Product p) {
        pR.save(p);
    }

    @Override
    public List<Product> list() {
        return pR.findAll();
    }

    @Override
    public void delete(Long id) {
        pR.deleteById(id);

    }

    @Override
    public Optional<Product> listId(Long id) {
        return pR.findById(id);
    }

    @Override
    public void update(Product pro) {
        pR.save(pro);

    }
}
