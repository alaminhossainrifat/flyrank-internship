package com.flyrank.auth_service.controller;

import com.flyrank.auth_service.dto.AuthRequest;
import com.flyrank.auth_service.dto.ErrorResponse;
import com.flyrank.auth_service.service.SupabaseAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller exposing authentication endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SupabaseAuthService authService;

    public AuthController(SupabaseAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody(required = false) AuthRequest request) {
        if (request == null || isNullOrEmpty(request.getEmail()) || isNullOrEmpty(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email and password are required"));
        }
        return authService.signUp(request.getEmail(), request.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = false) AuthRequest request) {
        if (request == null || isNullOrEmpty(request.getEmail()) || isNullOrEmpty(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Email and password are required"));
        }
        return authService.login(request.getEmail(), request.getPassword());
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
