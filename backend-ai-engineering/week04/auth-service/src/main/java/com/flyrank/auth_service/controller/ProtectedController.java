package com.flyrank.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller holding routes requiring authorization.
 */
@RestController
@RequestMapping("/protected")
public class ProtectedController {

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Access token required");
        }
        return ResponseEntity.ok(authentication.getPrincipal());
    }
}
