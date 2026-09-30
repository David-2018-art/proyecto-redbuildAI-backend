package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface    IProductRepository extends JpaRepository<Product,Long> {
    boolean existsByCategory_IdCategory(Long idCategory);

    @Query("""
        SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
        FROM Product p
        WHERE p.user.idUser = :idUser
        """)
    boolean existsByUser_IdUser(@Param("idUser") Long idUser);
}
