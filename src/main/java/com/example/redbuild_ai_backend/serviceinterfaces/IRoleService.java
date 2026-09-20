package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.Role;


import java.util.List;
import java.util.Optional;


public interface IRoleService {

    public void insert(Role r);
    public List<Role> list();
    public void delete(Long id);
    public Optional<Role> listId(Long id);
    public void update(Role role);
    public List<Role> listByStatus(String statusRole);
}
