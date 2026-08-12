package com.alaminhossainrifat.ai_decision_flow.controller;

import com.alaminhossainrifat.ai_decision_flow.dto.AiDecisionRequest;
import com.alaminhossainrifat.ai_decision_flow.dto.AiDecisionResponse;
import com.alaminhossainrifat.ai_decision_flow.service.AiDecisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/decision")
@CrossOrigin(origins = "*") // Allows React frontend to call this API
public class DecisionController {

    private final AiDecisionService aiDecisionService;

    public DecisionController(AiDecisionService aiDecisionService) {
        this.aiDecisionService = aiDecisionService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<AiDecisionResponse> evaluateDecision(@RequestBody AiDecisionRequest request) {
        if (request.getPrompt() == null || request.getPrompt().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(new AiDecisionResponse("NO"));
        }

        String result = aiDecisionService.evaluatePrompt(request.getPrompt());
        return ResponseEntity.ok(new AiDecisionResponse(result));
    }
}