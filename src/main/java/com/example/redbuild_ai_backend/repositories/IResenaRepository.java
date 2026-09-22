package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IResenaRepository extends JpaRepository<Resena, Long> {

    // Consulta simple (JPQL): devuelve todas las reseñas.
    // Uso: equivalente a findAll(), mantiene el mapeo a la entidad Resena.
    @Query("SELECT r FROM Resena r")
    List<Resena> findAllResenas();

    // JPQL con JOIN FETCH: devuelve reseñas del usuario y trae la entidad User en la misma consulta.
    // Requiere que Resena tenga campo "user" mapeado con @ManyToOne.
    @Query("SELECT r FROM Resena r JOIN FETCH r.user u WHERE u.idUser = :userId")
    List<Resena> findByUserId(@Param("userId") Long userId);

    // Consulta con JOIN (SQL nativo): devuelve columnas de Resenas junto a datos del Usuario.
    // Nota: esta consulta asume que existe una FK en la tabla 'Resenas' llamada 'id_user'
    // que referencia 'Usuarios.idUser'. Como la entidad Resena en el modelo de datos actual
    // no contiene un mapeo a User, la consulta se declara como nativeQuery y retorna List<Object[]>
    // donde cada Object[] contiene todas las columnas de Resenas seguidas por las columnas
    // seleccionadas de Usuarios (userId, userName, userEmail).
    // Uso típico: obtener reseñas filtradas por usuario junto con información mínima del usuario.
    @Query(value = "SELECT r.*, u.idUser AS userId, u.nameUser AS userName, u.emailUser AS userEmail " +
            "FROM Resenas r JOIN Usuarios u ON r.id_user = u.idUser " +
            "WHERE u.idUser = :userId", nativeQuery = true)
    List<Object[]> findResenasByUserId(@Param("userId") Long userId);
}
