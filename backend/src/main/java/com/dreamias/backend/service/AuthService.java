package com.dreamias.backend.service;

import com.dreamias.backend.dto.AuthResponse;
import com.dreamias.backend.dto.LoginRequest;
import com.dreamias.backend.dto.SignupRequest;
import com.dreamias.backend.entity.Role;
import com.dreamias.backend.entity.User;
import com.dreamias.backend.repository.UserRepository;
import com.dreamias.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
                       JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTargetYear(request.getTargetYear());
        user.setRole(Role.USER); // Default role for new signups

        userRepository.save(user);

        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponse(jwtToken, user.getName(), user.getEmail(), user.getRole().name());
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtUtil.generateToken(user);
        return new AuthResponse(jwtToken, user.getName(), user.getEmail(), user.getRole().name());
    }
}
