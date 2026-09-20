package com.example.redbuild_ai_backend.serviceinterfaces;

import com.example.redbuild_ai_backend.entities.User;


import java.util.List;
import java.util.Optional;


public interface IUserService {

    public void insert(User u);
    public List<User> list();
    public void delete(Long id);
    public Optional<User> listId(Long id);
    public void update(User user);
    public List<User> listByStatus(String statusUser);
}
