package com.flyrank.auth_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller managing public endpoints accessible without authentication.
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/info")
    public ResponseEntity<?> getPublicInfo() {
        return ResponseEntity.ok(Map.of("message", "Welcome stranger! This info is public."));
    }
}
