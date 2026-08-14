package com.alaminhossianrifat.llm_api;

import com.alaminhossianrifat.llm_api.dto.SupportResponse;
import com.alaminhossianrifat.llm_api.service.LlmService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EvalRunnerTest {

    @Autowired
    private LlmService llmService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void runEvaluation() throws Exception {
        File file = new File("evals/cases.json");
        List<Map<String, String>> cases = objectMapper.readValue(file, new TypeReference<>() {});

        int total = cases.size();
        int passed = 0;

        System.out.println("==================================================");
        System.out.println("            RUNNING LLM EVALUATION SET           ");
        System.out.println("==================================================");

        for (int i = 0; i < cases.size(); i++) {
            Map<String, String> testCase = cases.get(i);
            String input = testCase.get("input");
            String expected = testCase.get("expected_category");

            try {
                SupportResponse response = llmService.callLlm(input);
                String actual = response.getCategory();

                if (expected.equalsIgnoreCase(actual)) {
                    passed++;
                    System.out.printf("[%d/%d] PASS | Input: \"%s\" -> Got: %s%n", (i + 1), total, input, actual);
                } else {
                    System.out.printf("[%d/%d] FAIL | Input: \"%s\" -> Expected: %s, Got: %s%n", (i + 1), total, input, expected, actual);
                }
            } catch (Exception e) {
                System.out.printf("[%d/%d] ERROR | Input: \"%s\" -> Error: %s%n", (i + 1), total, input, e.getMessage());
            }
        }

        double score = ((double) passed / total) * 100;
        System.out.println("==================================================");
        System.out.printf("EVAL RESULT: %d/%d Passed (%.1f%% Accuracy)%n", passed, total, score);
        System.out.println("==================================================");
    }
}