package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Category;
import com.example.redbuild_ai_backend.repositories.ICategoryRepository;
import com.example.redbuild_ai_backend.repositories.IProductRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplement implements ICategoryService {
    private final ICategoryRepository cP;
    private final IProductRepository pR;

    public CategoryServiceImplement(ICategoryRepository cP, IProductRepository pR) {
        this.cP = cP;
        this.pR = pR;
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
        if (pR.existsByCategory_IdCategory(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la categoria porque tiene productos asociados"
            );
        }

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

    @Override
    public List<Category> buscarPorEstado(String estado) {
        return cP.buscarPorEstado(estado);
    }

    @Override
    public List<Object[]> contarProductosPorCategoria() {
        return cP.contarProductosPorCategoria();
    }
}
