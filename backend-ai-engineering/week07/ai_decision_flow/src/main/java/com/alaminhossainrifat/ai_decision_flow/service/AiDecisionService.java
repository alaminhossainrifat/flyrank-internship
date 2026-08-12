package com.alaminhossainrifat.ai_decision_flow.service;

import org.springframework.stereotype.Service;

@Service
public class AiDecisionService {

    /*
     * REAL OPENAI IMPLEMENTATION (Commented out to avoid 429 Insufficient Quota Error)
     *
     * private final ChatClient chatClient;
     * public AiDecisionService(ChatClient.Builder chatClientBuilder) {
     *     this.chatClient = chatClientBuilder.defaultSystem("...").build();
     * }
     *
     * public String evaluatePromptWithRealAI(String userPrompt) {
     *     return this.chatClient.prompt().user(userPrompt).call().content().trim().toUpperCase();
     * }
     */

    /**
     * MOCK IMPLEMENTATION: Evaluates the prompt locally to bypass API costs.
     * Returns YES if the prompt contains support-related keywords, otherwise NO.
     */
    public String evaluatePrompt(String userPrompt) {
        if (userPrompt == null || userPrompt.trim().isEmpty()) {
            return "NO";
        }

        String lowerPrompt = userPrompt.toLowerCase();

        // Mock logic: Assuming support requests contain these keywords
        if (lowerPrompt.contains("support") ||
                lowerPrompt.contains("help") ||
                lowerPrompt.contains("issue") ||
                lowerPrompt.contains("error") ||
                lowerPrompt.contains("broken") ||
                lowerPrompt.contains("won't turn on")) {
            return "YES";
        }

        // Default to NO for sales, general questions, etc.
        return "NO";
    }
}