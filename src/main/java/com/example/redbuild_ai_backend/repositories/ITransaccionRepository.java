package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransaccionRepository extends JpaRepository<Transaccion, Long> {

    // Consulta simple (JPQL): devuelve todas las transacciones.
    // Uso: equivalente a findAll(), mantiene el mapeo a la entidad Transaccion.
    @Query("SELECT t FROM Transaccion t")
    List<Transaccion> findAllTransacciones();

    // JPQL con JOIN FETCH: devuelve transacciones del usuario y trae la entidad User en la misma consulta.
    // Requiere que Transaccion tenga campo "user" mapeado con @ManyToOne.
    @Query("SELECT t FROM Transaccion t JOIN FETCH t.user u WHERE u.idUser = :userId")
    List<Transaccion> findByUserId(@Param("userId") Long userId);

    // Consulta con JOIN (SQL nativo): devuelve columnas de Transacciones junto a datos del Usuario.
    // Nota: esta consulta asume que existe una FK en la tabla 'Transacciones' llamada 'id_user'
    // que referencia 'Usuarios.idUser'. Como la entidad Transaccion en el modelo de datos actual
    // no contiene un mapeo a User, la consulta se declara como nativeQuery y retorna List<Object[]>
    // donde cada Object[] contiene todas las columnas de Transacciones seguidas por las columnas
    // seleccionadas de Usuarios (userId, userName, userEmail).
    // Uso típico: obtener transacciones junto con información mínima del usuario que las realizó.
    @Query(value = "SELECT t.*, u.idUser AS userId, u.nameUser AS userName, u.emailUser AS userEmail " +
            "FROM Transacciones t JOIN Usuarios u ON t.id_user = u.idUser " +
            "WHERE u.idUser = :userId", nativeQuery = true)
    List<Object[]> findTransactionsByUserId(@Param("userId") Long userId);
}
