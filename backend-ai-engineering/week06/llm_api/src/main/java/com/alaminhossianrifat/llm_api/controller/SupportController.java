package com.alaminhossianrifat.llm_api.controller;

import com.alaminhossianrifat.llm_api.dto.SupportRequest;
import com.alaminhossianrifat.llm_api.dto.SupportResponse;
import com.alaminhossianrifat.llm_api.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SupportController {

    @Value("${llm.stub:1}")
    private String llmStub;

    @Autowired
    private PromptService promptService;

    @PostMapping("/triage")
    public ResponseEntity<?> triageMessage(@Valid @RequestBody SupportRequest request) {
        try {
            String prompt = promptService.loadPrompt("triage-v1");
            System.out.println(prompt);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error loading prompt");
        }

        if ("1".equals(llmStub)) {
            SupportResponse stubResponse = new SupportResponse("billing", "normal", 0.95, "Stub response for testing.");
            return ResponseEntity.ok(stubResponse);
        }

        SupportResponse liveResponse = new SupportResponse("other", "low", 0.50, "Live model response pending.");
        return ResponseEntity.ok(liveResponse);
    }
}