package com.example.redbuild_ai_backend.controllers;

import com.example.redbuild_ai_backend.dtos.LoginRequestDTO;
import com.example.redbuild_ai_backend.dtos.LoginResponseDTO;
import com.example.redbuild_ai_backend.entities.User;
import com.example.redbuild_ai_backend.repositories.IUserRepository;
import com.example.redbuild_ai_backend.securities.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final IUserRepository userRepository;

    public LoginController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, IUserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmailUser(),
                                request.getPasswordUser()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtTokenService.generateToken(userDetails);

        User user = userRepository
                .findByEmailUser(request.getEmailUser())
                .orElseThrow();

        return ResponseEntity.ok(
                new LoginResponseDTO(
                        token,
                        user.getIdUser(),
                        user.getNameUser(),
                        user.getEmailUser(),
                        user.getRole().getNameRole()
                )
        );
    }

}
