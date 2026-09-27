package com.example.redbuild_ai_backend.controllers;
import com.example.redbuild_ai_backend.dtos.ChangePasswordDTO;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.exceptions.ResourceNotFoundException;
import com.example.redbuild_ai_backend.serviceinterfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/Users")
public class PasswordController {

        private final IUserService uS;
        private final PasswordEncoder passwordEncoder;

        public PasswordController(
                IUserService uS,
                PasswordEncoder passwordEncoder) {
            this.uS = uS;
            this.passwordEncoder = passwordEncoder;
        }

        @PutMapping("/{id}/password")
        public ResponseEntity<String> cambiarPassword(
                @PathVariable("id") Long id,
                @Valid @RequestBody ChangePasswordDTO dto,
                Authentication authentication) {

            User user = uS.listId(id)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "No existe el usuario con ID: " + id
                    ));

            boolean esPropietario = user.getEmailUser()
                    .equalsIgnoreCase(authentication.getName());

            if (!esPropietario) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "Solo puedes cambiar tu propia contraseña"
                );
            }

            if (!passwordEncoder.matches(
                    dto.getCurrentPassword(),
                    user.getPasswordUser())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La contraseña actual es incorrecta"
                );
            }

            // BCrypt admite como máximo 72 bytes.
            if (dto.getNewPassword()
                    .getBytes(StandardCharsets.UTF_8).length > 72) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La nueva contraseña supera el límite de 72 bytes"
                );
            }

            if (passwordEncoder.matches(
                    dto.getNewPassword(),
                    user.getPasswordUser())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La nueva contraseña debe ser diferente de la actual"
                );
            }

            user.setPasswordUser(
                    passwordEncoder.encode(dto.getNewPassword())
            );

            uS.update(user);

            return ResponseEntity.ok(
                    "Contraseña actualizada correctamente"
            );
        }
}
