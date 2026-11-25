package com.example.redbuild_ai_backend.configs;

import com.example.redbuild_ai_backend.entities.Role;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IRoleRepository;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements ApplicationRunner {

    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    public AdminAccountInitializer(
            IRoleRepository roleRepository,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder,
            Environment environment) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) {
        String email = environment.getProperty("ADMIN_EMAIL", "").trim();
        String password = environment.getProperty("ADMIN_PASSWORD", "");

        if (email.isEmpty() || password.isBlank()) {
            return;
        }

        if (userRepository.findByEmailUser(email).isPresent()) {
            return;
        }

        Role administrator = roleRepository.findByNameRole("Administrador")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setNameRole("Administrador");
                    role.setDescriptionRole("Administración completa del sistema");
                    role.setStatusRole("Activo");
                    return roleRepository.save(role);
                });

        User user = new User();
        user.setNameUser("Hubert");
        user.setLastNameUser("Ponte");
        user.setEmailUser(email);
        user.setPasswordUser(passwordEncoder.encode(password));
        user.setPhoneUser("");
        user.setCompanyNameUser("");
        user.setStatusUser("Activo");
        user.setRole(administrator);

        userRepository.save(user);
    }
}
