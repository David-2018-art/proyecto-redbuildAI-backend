package com.example.redbuild_ai_backend.securities;

import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Objects;

@Service
public class JwtTokenService {

    private static final long TOKEN_VALIDITY = 5 * 60 * 60;

    private final JwtEncoder jwtEncoder;
    private final IUserRepository userRepository;

    public JwtTokenService(
            JwtEncoder jwtEncoder,
            IUserRepository userRepository) {

        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public String generateToken(UserDetails userDetails) {

        User user = userRepository
                .findByEmailUser(userDetails.getUsername())
                .orElseThrow(() -> new BadCredentialsException(
                        "No se pudo iniciar sesión"
                ));

        if (!"Activo".equalsIgnoreCase(user.getStatusUser())
                || user.getRole() == null
                || !"Activo".equalsIgnoreCase(
                user.getRole().getStatusRole())
                || user.getRole().getNameRole() == null
                || user.getRole().getNameRole().isBlank()
                || user.getTokenVersion() == null) {

            throw new BadCredentialsException(
                    "La cuenta no está habilitada para iniciar sesión"
            );
        }

        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmailUser())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(TOKEN_VALIDITY))
                .claim("uid", user.getIdUser())
                .claim("ver", user.getTokenVersion())
                .claim("roles", user.getRole().getNameRole())
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS512)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}