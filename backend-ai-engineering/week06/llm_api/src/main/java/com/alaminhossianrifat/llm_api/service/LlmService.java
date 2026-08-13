package com.alaminhossianrifat.llm_api.service;

import com.alaminhossianrifat.llm_api.dto.SupportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LlmService {

    private static final Logger log = LoggerFactory.getLogger(LlmService.class);

    @Autowired
    private OpenAIClient client;

    @Autowired
    private PromptService promptService;

    @Value("${llm.model}")
    private String modelName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SupportResponse callLlm(String userText) throws Exception {
        String promptVersion = "triage-v1";
        String systemPrompt = promptService.loadPrompt(promptVersion);

        long startTime = System.currentTimeMillis();
        int repairsNeeded = 0;

        ChatCompletion completion = sendRequest(systemPrompt, userText);
        String rawResponse = completion.choices().get(0).message().content().orElse("{}");

        SupportResponse response;
        try {
            response = parseAndValidate(rawResponse);
        } catch (Exception e) {
            repairsNeeded++;
            log.warn("Parsing/validation failed. Triggering repair retry for error: {}", e.getMessage());

            String repairPrompt = systemPrompt + "\nYour previous answer was rejected: " + e.getMessage() + ". Return only corrected JSON matching the schema.";
            completion = sendRequest(repairPrompt, userText);
            rawResponse = completion.choices().get(0).message().content().orElse("{}");

            try {
                response = parseAndValidate(rawResponse);
            } catch (Exception ex) {
                logQuarantine(userText, rawResponse, ex.getMessage(), promptVersion);
                throw new IllegalArgumentException("Validation failed after repair: " + ex.getMessage());
            }
        }

        long duration = System.currentTimeMillis() - startTime;
        logCost(promptVersion, completion, duration, repairsNeeded);

        return response;
    }

    private ChatCompletion sendRequest(String systemPrompt, String userText) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(modelName)
                .messages(List.of(
                        ChatCompletionMessageParam.ofSystem(
                                ChatCompletionSystemMessageParam.builder().content(systemPrompt).build()
                        ),
                        ChatCompletionMessageParam.ofUser(
                                ChatCompletionUserMessageParam.builder().content(userText).build()
                        )
                ))
                .temperature(0.0)
                .build();

        return client.chat().completions().create(params);
    }

    private SupportResponse parseAndValidate(String jsonStr) throws Exception {
        String cleanedJson = jsonStr.replaceAll("```json", "").replaceAll("```", "").trim();
        int startIndex = cleanedJson.indexOf("{");
        int endIndex = cleanedJson.lastIndexOf("}");
        if (startIndex != -1 && endIndex != -1) {
            cleanedJson = cleanedJson.substring(startIndex, endIndex + 1);
        }

        SupportResponse response = objectMapper.readValue(cleanedJson, SupportResponse.class);

        if (!List.of("billing", "bug", "feature", "other").contains(response.getCategory())) {
            throw new IllegalArgumentException("Invalid category: " + response.getCategory());
        }

        return response;
    }

    private void logCost(String promptVersion, ChatCompletion completion, long durationMs, int repairsNeeded) {
        long promptTokens = completion.usage().map(u -> u.promptTokens()).orElse(0L);
        long completionTokens = completion.usage().map(u -> u.completionTokens()).orElse(0L);

        log.info("COST LOG | version={} | model={} | prompt_tokens={} | completion_tokens={} | duration_ms={} | repairs={}",
                promptVersion, modelName, promptTokens, completionTokens, durationMs, repairsNeeded);
    }

    private void logQuarantine(String input, String rawOutput, String error, String version) {
        try {
            Files.createDirectories(Paths.get("logs"));
            try (FileWriter fw = new FileWriter("logs/quarantine.jsonl", true);
                 PrintWriter pw = new PrintWriter(fw)) {

                Map<String, String> logMap = new HashMap<>();
                logMap.put("version", version);
                logMap.put("input", input);
                logMap.put("rawOutput", rawOutput);
                logMap.put("error", error);

                pw.println(objectMapper.writeValueAsString(logMap));
            }
        } catch (Exception e) {
            log.error("Failed to write to quarantine log", e);
        }
    }
}