package com.ecommerce.api.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticatedUser {
    private String username;
    private String role;
    private String email;
    private Integer userId;
}
