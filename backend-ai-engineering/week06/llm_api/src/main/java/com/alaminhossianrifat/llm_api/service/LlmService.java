package com.alaminhossianrifat.llm_api.service;

import com.alaminhossianrifat.llm_api.dto.SupportResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessageParam;
import com.openai.models.chat.completions.ChatCompletionSystemMessageParam;
import com.openai.models.chat.completions.ChatCompletionUserMessageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LlmService {

    @Autowired
    private OpenAIClient client;

    @Autowired
    private PromptService promptService;

    @Value("${llm.model}")
    private String modelName;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SupportResponse callLlm(String userText) throws Exception {
        String systemPrompt = promptService.loadPrompt("triage-v1");

        String rawResponse = sendRequest(systemPrompt, userText);

        try {
            return parseAndValidate(rawResponse);
        } catch (Exception e) {
            String repairPrompt = systemPrompt + "\nYour previous answer was rejected: " + e.getMessage() + ". Return only corrected JSON matching the schema.";
            String repairedResponse = sendRequest(repairPrompt, userText);
            return parseAndValidate(repairedResponse);
        }
    }

    private String sendRequest(String systemPrompt, String userText) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(modelName)
                .messages(List.of(
                        ChatCompletionMessageParam.ofSystem(
                                ChatCompletionSystemMessageParam.builder()
                                        .content(systemPrompt)
                                        .build()
                        ),
                        ChatCompletionMessageParam.ofUser(
                                ChatCompletionUserMessageParam.builder()
                                        .content(userText)
                                        .build()
                        )
                ))
                .temperature(0.0)
                .build();

        ChatCompletion completion = client.chat().completions().create(params);
        return completion.choices().get(0).message().content().orElse("{}");
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
}