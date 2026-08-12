package com.alaminhossainrifat.ai_decision_flow.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AiDecisionService {

    private final ChatClient chatClient;

    public AiDecisionService(ChatClient.Builder chatClientBuilder) {
        // Configuring the ChatClient with a strict system prompt
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a strict decision-making AI. " +
                        "Evaluate the user's prompt and respond with EXACTLY one word: either 'YES' or 'NO'. " +
                        "Do not add punctuation, explanations, or any other text.")
                .build();
    }

    /**
     * Sends the prompt to OpenAI and retrieves a YES/NO response.
     */
    public String evaluatePrompt(String userPrompt) {
        String response = this.chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();

        // Cleaning the response to ensure no hidden spaces or characters exist
        if (response != null) {
            response = response.trim().toUpperCase();
        }

        // Fallback safety check
        if (!"YES".equals(response) && !"NO".equals(response)) {
            return "NO"; // Default fallback
        }

        return response;
    }
}