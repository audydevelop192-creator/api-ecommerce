package com.ecommerce.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfileResponse {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String role;
}
