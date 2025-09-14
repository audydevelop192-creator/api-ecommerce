package com.ecommerce.api.utils;

import com.ecommerce.api.config.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * Ambil userId dari SecurityContext
     */
    public static Long getCurrentUserId() {
        AuthenticatedUser authUser = getCurrentUser();
        return authUser != null ? authUser.getUserId() : null;
    }

    /**
     * Ambil username dari SecurityContext
     */
    public static String getCurrentUsername() {
        AuthenticatedUser authUser = getCurrentUser();
        return authUser != null ? authUser.getUsername() : null;
    }

    /**
     * Ambil principal
     */
    public static AuthenticatedUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AuthenticatedUser) {
            return (AuthenticatedUser) principal;
        }
        return null;
    }
}
