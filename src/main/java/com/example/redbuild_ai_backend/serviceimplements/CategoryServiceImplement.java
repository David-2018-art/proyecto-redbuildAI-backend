package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Category;
import com.example.redbuild_ai_backend.repositories.ICategoryRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements ICategoryService {
    private final ICategoryRepository cP;

    public CategoryServiceImplement(ICategoryRepository cP) {
        this.cP = cP;
    }

    @Override
    public void insert(Category c) {
        cP.save(c);
    }

    @Override
    public List<Category> list() {
        return cP.findAll();
    }

    @Override
    public void delete(Long id) {
        cP.deleteById(id);

    }

    @Override
    public Optional<Category> listId(Long id) {
        return cP.findById(id);
    }

    @Override
    public void update(Category cat) {
        cP.save(cat);
    }
}
