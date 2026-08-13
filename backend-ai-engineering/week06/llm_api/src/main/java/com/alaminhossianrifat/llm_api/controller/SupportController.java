package com.alaminhossianrifat.llm_api.controller;

import com.alaminhossianrifat.llm_api.dto.SupportRequest;
import com.alaminhossianrifat.llm_api.dto.SupportResponse;
import com.alaminhossianrifat.llm_api.service.LlmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SupportController {

    @Value("${llm.stub:1}")
    private String llmStub;

    @Value("${llm.enabled:true}")
    private boolean llmEnabled;

    @Autowired
    private LlmService llmService;

    @PostMapping("/triage")
    public ResponseEntity<?> triageMessage(@Valid @RequestBody SupportRequest request) {
        if (!llmEnabled) {
            SupportResponse fallback = new SupportResponse("other", "low", 0.0, "Service temporarily disabled (Kill switch active).");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallback);
        }

        if ("1".equals(llmStub)) {
            SupportResponse stubResponse = new SupportResponse("billing", "normal", 0.95, "Stub response for testing.");
            return ResponseEntity.ok(stubResponse);
        }

        try {
            SupportResponse liveResponse = llmService.callLlm(request.getText());
            return ResponseEntity.ok(liveResponse);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(422).body("Validation error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(504).body("Service error or timeout: " + e.getMessage());
        }
    }
}