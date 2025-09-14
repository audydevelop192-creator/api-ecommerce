package com.ecommerce.api.auth;

import com.ecommerce.api.dto.request.RegisterRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.RegisterResponse;
import com.ecommerce.api.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
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
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());

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

}