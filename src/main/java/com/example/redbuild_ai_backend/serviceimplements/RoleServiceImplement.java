package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.Role;
import com.example.redbuild_ai_backend.repositories.IRoleRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IRoleService;
import org.springframework.stereotype.Service;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplement implements IRoleService {
    private final IRoleRepository rR;
    private final IUserRepository uP;

    public RoleServiceImplement(IRoleRepository rR, IUserRepository uP) {
        this.rR = rR;
        this.uP = uP;
    }

    @Override
    public void insert(Role r) {
        rR.save(r);
    }

    @Override
    public List<Role> list() {
        return rR.findAll();
    }

    @Override
    public void delete(Long id) {
        if (uP.existsByRole_IdRole(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El rol tiene usuarios asignados");
        }
        rR.deleteById(id);

    }

    @Override
    public Optional<Role> listId(Long id) {
        return rR.findById(id);
    }

    @Override
    public void update(Role role) {
        rR.save(role);
    }
}
