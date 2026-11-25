package com.example.redbuild_ai_backend.serviceimplements;

import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public JwtUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailUser) {

        User user = userRepository.findByEmailUser(emailUser)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Credenciales incorrectas"
                ));

        if (!"Activo".equalsIgnoreCase(user.getStatusUser())
                || user.getRole() == null
                || !"Activo".equalsIgnoreCase(
                user.getRole().getStatusRole())
                || user.getRole().getNameRole() == null
                || user.getRole().getNameRole().isBlank()) {

            throw new DisabledException(
                    "El usuario o su rol no está habilitado"
            );
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmailUser())
                .password(user.getPasswordUser())
                .authorities(user.getRole().getNameRole())
                .build();
    }
}