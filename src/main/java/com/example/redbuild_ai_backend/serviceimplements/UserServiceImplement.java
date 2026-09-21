package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {
    private final IUserRepository uR;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImplement(IUserRepository uR, PasswordEncoder passwordEncoder) {
        this.uR = uR;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void insert(User u) {
        u.setPasswordUser(passwordEncoder.encode(u.getPasswordUser()));
        uR.save(u);
    }

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    public void delete(Long id) {
        uR.deleteById(id);

    }

    @Override
    public Optional<User> listId(Long id) {
        return uR.findById(id);
    }

    @Override
    public void update(User user) {
        uR.save(user);
    }

    @Override
    public List<User> listByStatus(String statusUser) {
        return uR.findByStatusUser(statusUser);
    }

}
