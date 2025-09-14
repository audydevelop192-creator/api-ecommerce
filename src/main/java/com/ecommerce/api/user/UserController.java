package com.ecommerce.api.user;

import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.UserProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserProfileResponse>> profile() {
        BaseResponse<UserProfileResponse> response = userService.profile();
        return ResponseEntity.ok(response);
    }
}
