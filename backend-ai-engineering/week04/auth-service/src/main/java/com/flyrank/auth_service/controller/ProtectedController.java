package com.flyrank.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller providing protected endpoints guarded by JwtAuthFilter middleware.
 */
@RestController
@RequestMapping("/protected")
public class ProtectedController {

    /**
     * Retrieves authenticated user's private profile metadata.
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token required"));
        }
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    /**
     * Second protected endpoint verifying middleware reusability.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Access token required"));
        }

        Object principal = authentication.getPrincipal();
        String email = "User";

        if (principal instanceof Map<?, ?> userMap) {
            Object emailObj = userMap.get("email");
            if (emailObj != null) {
                email = emailObj.toString();
            }
        }

        return ResponseEntity.ok(Map.of("message", "Welcome to dashboard, " + email + "!"));
    }
}