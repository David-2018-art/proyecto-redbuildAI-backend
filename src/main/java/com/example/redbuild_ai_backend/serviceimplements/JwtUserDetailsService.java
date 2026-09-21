package com.example.redbuild_ai_backend.serviceimplements;


import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final IUserRepository userRepository;

    public JwtUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailUser)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmailUser(emailUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado: " + emailUser
                        )
                );

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(
                        user.getRole().getNameRole()
                )
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmailUser())
                .password(user.getPasswordUser())
                .authorities(authorities)
                .disabled(!user.getStatusUser().equalsIgnoreCase("Activo"))
                .build();
    }



}
