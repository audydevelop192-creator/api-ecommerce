package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class RegisterResponse {
    private Integer id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    public RegisterResponse(Integer id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}
