package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    public void insert(Product p);
    public List<Product> list();
    public void delete(Long id);
    public Optional<Product> listId(Long id);
    public void update(Product pro);
}
