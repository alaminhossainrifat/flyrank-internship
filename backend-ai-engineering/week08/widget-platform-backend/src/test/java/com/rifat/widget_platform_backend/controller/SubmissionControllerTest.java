package com.rifat.widget_platform_backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
public class SubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Replace this with a valid Widget ID from your database for tests to pass
    private final String VALID_WIDGET_ID = "fedea9ec-fa6a-4536-b916-1d0b11d2304b";

    @Test
    public void testCorsPreflightRequest() throws Exception {
        mockMvc.perform(options("/api/submissions?widgetId=" + VALID_WIDGET_ID)
                        .header("Origin", "http://example.com")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access-Control-Allow-Origin"));
    }

    @Test
    public void testEmptyPayloadValidation() throws Exception {
        mockMvc.perform(post("/api/submissions?widgetId=" + VALID_WIDGET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Submission payload cannot be empty"));
    }

    @Test
    public void testOversizedPayloadValidation() throws Exception {
        String largeText = "a".repeat(2001);
        String payload = "{\"message\": \"" + largeText + "\"}";

        mockMvc.perform(post("/api/submissions?widgetId=" + VALID_WIDGET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isPayloadTooLarge())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    public void testHoneypotSpamProtection() throws Exception {
        String payload = "{\"name\": \"Spam Bot\", \"_bot_check\": \"I am a bot\"}";

        mockMvc.perform(post("/api/submissions?widgetId=" + VALID_WIDGET_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Submission received"));
    }
}