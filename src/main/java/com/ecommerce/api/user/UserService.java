package com.ecommerce.api.user;

import com.ecommerce.api.config.AuthenticatedUser;
import com.ecommerce.api.dto.response.BaseResponse;
import com.ecommerce.api.dto.response.UserProfileResponse;
import com.ecommerce.api.utils.SecurityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BaseResponse<UserProfileResponse> profile() {
        AuthenticatedUser authenticatedUser = SecurityUtils.getCurrentUser();
        if (authenticatedUser == null) {
            return new BaseResponse<>("error", "User not found", null);
        }
        UserProfileResponse userProfileResponse = new UserProfileResponse();
        userProfileResponse.setId(authenticatedUser.getUserId());
        userProfileResponse.setUsername(authenticatedUser.getUsername());
        userProfileResponse.setEmail(authenticatedUser.getEmail());
        userProfileResponse.setRole(authenticatedUser.getRole());
        return new BaseResponse<>("success", "User profile", userProfileResponse);
    }
}
