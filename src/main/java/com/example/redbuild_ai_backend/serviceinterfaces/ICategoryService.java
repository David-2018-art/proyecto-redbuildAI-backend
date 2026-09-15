package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Category;


import java.util.List;
import java.util.Optional;


public interface ICategoryService {

    public void insert(Category c);
    public List<Category> list();
    public void delete(Long id);
    public Optional<Category> listId(Long id);
    public void update(Category cat);

    public List<Category> buscarPorEstado(String estado);
    public List<Object[]> contarProductosPorCategoria();
}
