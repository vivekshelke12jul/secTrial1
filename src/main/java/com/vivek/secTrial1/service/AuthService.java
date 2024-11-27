package com.vivek.secTrial1.service;

import com.vivek.secTrial1.controllers.exchanges.AuthResponse;
import com.vivek.secTrial1.controllers.exchanges.LoginRequest;
import com.vivek.secTrial1.controllers.exchanges.RegisterRequest;
import com.vivek.secTrial1.model.Role;
import com.vivek.secTrial1.model.User;
import com.vivek.secTrial1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthResponse register(RegisterRequest req) {
        User user = new User(req.getUsername(),
                        passwordEncoder.encode(req.getPassword()),
                        Role.ROLE_USER);

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public AuthResponse login(LoginRequest req) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        authenticationManager.authenticate(token);

        User user = userRepository.findByUsername(req.getUsername());
        String jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();

    }
}
