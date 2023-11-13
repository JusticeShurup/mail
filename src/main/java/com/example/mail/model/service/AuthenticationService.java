package com.example.mail.model.service;


import com.example.mail.model.Enum.Role;
import com.example.mail.model.domain.AuthenticationResponse;
import com.example.mail.model.domain.RegisterRequest;
import com.example.mail.model.domain.AuthenticationRequest;
import com.example.mail.model.domain.User;
import com.example.mail.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    private User buildUser(RegisterRequest request) {
        return User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
    }

    public User registerAndReturnUser(RegisterRequest request) { // Function to register user used for backend queries
        var user = buildUser(request);


        var refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        repository.save(user);

        return user;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = buildUser(request);

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        repository.save(user);


        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .roles(new Role[]{user.getRole()})
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        var accessToken = jwtService.generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .roles(new Role[]{user.getRole()})
                .build();
    }
}
