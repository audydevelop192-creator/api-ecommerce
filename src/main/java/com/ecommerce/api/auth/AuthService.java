package com.ecommerce.api.auth;

import com.ecommerce.api.dto.request.LoginRequest;
import com.ecommerce.api.dto.request.RegisterRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.LoginResponse;
import com.ecommerce.api.dto.response.RegisterResponse;
import com.ecommerce.api.model.User;
import com.ecommerce.api.utils.JwtUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtUtils jwtUtils;

    public AuthService(AuthRepository authRepository, JwtUtils jwtUtils) {
        this.authRepository = authRepository;
        this.jwtUtils = jwtUtils;
    }

    public BaseResponse<RegisterResponse> register(RegisterRequest request) {
        if (request.getUsername() == null || request.getPassword() == null || request.getEmail() == null) {
            return new BaseResponse<>("error", "Username, password and email are required", null);
        }
        try {
            User existing = authRepository.findByUsername(request.getUsername());
            if (existing != null) {
                return new BaseResponse<>("error", "Username already taken", null);
            }
        } catch (Exception ignored) {}

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail().toLowerCase());
        newUser.setRole(request.getRole().toUpperCase());
        int result = authRepository.register(newUser);
        if (result > 0) {
            RegisterResponse data = new RegisterResponse(
                    newUser.getId(),
                    newUser.getUsername(),
                    newUser.getEmail(),
                    LocalDateTime.now()
            );
            return new BaseResponse<>("success", "User registered successfully", data);
        } else {
            return new BaseResponse<>("error", "Registration failed", null);
        }
    }

    public BaseResponse<LoginResponse> login(LoginRequest request) {
        if (request.getPassword() == null || request.getEmail() == null) {
            return new BaseResponse<>("error", "password and email are required", null);
        }
        User existing = authRepository.findByUsername(request.getEmail());
        if (existing != null && passwordEncoder.matches(request.getPassword(), existing.getPassword())) {
            String token = jwtUtils.generateToken(existing.getId(), existing.getUsername(), existing.getRole(), existing.getEmail());
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            return new BaseResponse<>("success", "Login successful", loginResponse);
        }
        return new BaseResponse<>("error", "invalid Email Or Password", null);
    }
}