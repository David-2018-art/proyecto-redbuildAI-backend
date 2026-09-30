package com.example.redbuild_ai_backend.securities;

import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class CustomJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final IUserRepository userRepository;

    public CustomJwtAuthenticationConverter(
            IUserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AbstractAuthenticationToken convert(Jwt jwt) {

        Object uidClaim = jwt.getClaims().get("uid");
        Object versionClaim = jwt.getClaims().get("ver");
        Object rolesClaim = jwt.getClaims().get("roles");

        if (!(uidClaim instanceof Number uid)
                || !(versionClaim instanceof String version)
                || !(rolesClaim instanceof String rolToken)) {

            throw tokenInvalido();
        }

        User user = userRepository.findById(uid.longValue())
                .orElseThrow(this::tokenInvalido);

        if (!"Activo".equalsIgnoreCase(user.getStatusUser())
                || user.getRole() == null
                || !"Activo".equalsIgnoreCase(
                user.getRole().getStatusRole())) {

            throw tokenInvalido();
        }

        String rolActual = user.getRole().getNameRole();

        if (rolActual == null
                || rolActual.isBlank()
                || !Objects.equals(
                jwt.getSubject(), user.getEmailUser())
                || !Objects.equals(
                version, user.getTokenVersion())
                || !Objects.equals(rolToken, rolActual)) {

            throw tokenInvalido();
        }

        return new JwtAuthenticationToken(
                jwt,
                List.of(new SimpleGrantedAuthority(rolActual)),
                user.getEmailUser()
        );
    }

    private InvalidBearerTokenException tokenInvalido() {
        return new InvalidBearerTokenException(
                "Token inválido o cuenta no habilitada. "
                        + "Inicia sesión nuevamente"
        );
    }
}