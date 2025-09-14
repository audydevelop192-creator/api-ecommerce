package com.ecommerce.api.auth;

import com.ecommerce.api.dto.request.RegisterRequest;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        BaseResponse<RegisterResponse> response = authService.register(request);
        return ResponseEntity.ok(response);
    }
}
