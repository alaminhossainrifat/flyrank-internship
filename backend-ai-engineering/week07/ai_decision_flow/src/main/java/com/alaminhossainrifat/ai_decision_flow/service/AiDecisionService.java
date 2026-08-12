package com.alaminhossainrifat.ai_decision_flow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiDecisionService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    public String evaluatePrompt(String userPrompt) {
        if (userPrompt == null || userPrompt.trim().isEmpty()) {
            return "NO";
        }

        try {
            RestTemplate restTemplate = new RestTemplate();
            // The exact OpenRouter URL from your cURL command
            String url = "https://openrouter.ai/api/v1/chat/completions";

            // Setting up Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // Setting up System Instruction
            String systemInstruction = "You are a strict decision-making AI. " +
                    "Evaluate the user's prompt and respond with EXACTLY one word: either 'YES' or 'NO'. " +
                    "Do not add punctuation, explanations, or any other text.";

            // Creating the Request Body (JSON)
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "google/gemma-4-26b-a4b-it:free"); // The model from your cURL

            Map<String, String> systemMessage = new HashMap<>();
            systemMessage.put("role", "system");
            systemMessage.put("content", systemInstruction);

            Map<String, String> userMessage = new HashMap<>();
            userMessage.put("role", "user");
            userMessage.put("content", userPrompt);

            requestBody.put("messages", List.of(systemMessage, userMessage));

            // Execute the API Call
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            // Parse the Response JSON
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    String aiResponse = (String) message.get("content");

                    if (aiResponse != null) {
                        aiResponse = aiResponse.trim().toUpperCase().replaceAll("[^A-Z]", "");
                        if (aiResponse.contains("YES")) return "YES";
                    }
                }
            }

            return "NO";

        } catch (Exception e) {
            System.err.println("OpenRouter Direct API Error: " + e.getMessage());
            return "NO"; // Fallback in case of API failure
        }
    }
}