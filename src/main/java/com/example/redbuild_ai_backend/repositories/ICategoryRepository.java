package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c FROM Category c WHERE c.statusCategory = :estado")
    public List<Category> buscarPorEstado(
            @Param("estado") String estado
    );

    @Query(value = "SELECT c.id_category,\n" +
            "                       c.name_category,\n" +
            "                       COUNT(p.id_product)\n" +
            "                FROM categorias c\n" +
            "                LEFT JOIN productos p\n" +
            "                    ON c.id_category = p.id_category\n" +
            "                GROUP BY c.id_category, c.name_category\n" +
            "                ORDER BY c.id_category", nativeQuery = true)
    public List<Object[]> contarProductosPorCategoria();
}
