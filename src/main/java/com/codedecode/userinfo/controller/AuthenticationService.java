package com.codedecode.userinfo.controller;

import com.codedecode.userinfo.config.JwtService;
import com.codedecode.userinfo.dto.UserDTO;
import com.codedecode.userinfo.entity.Role;
import com.codedecode.userinfo.entity.User;
import com.codedecode.userinfo.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .userName(request.getUserName())
                .city(request.getCity())
                .address(request.getAddress())
                .userPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)


                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);


        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserName(request.getUserName())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
