package com.alaminhossainrifat.ai_decision_flow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class HealthController {

    /**
     * Endpoint to verify if the Spring Boot application is up and running.
     * Accessible at: GET /api/system/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "AI Decision Flow backend is running successfully.");

        return ResponseEntity.ok(response);
    }
}
