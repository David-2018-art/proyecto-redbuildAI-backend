package com.example.redbuild_ai_backend.repositories;

import com.example.redbuild_ai_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    boolean existsByRole_IdRole(Long idRole);
    List<User> findByStatusUser(String statusUser);

    @Query(value = "select r.name_role, count(u.id_user) " +
            "from roles r left join usuarios u on r.id_role = u.id_role " +
            "group by r.id_role, r.name_role", nativeQuery = true)
    List<Object[]> getCountUsersByRole();
}
