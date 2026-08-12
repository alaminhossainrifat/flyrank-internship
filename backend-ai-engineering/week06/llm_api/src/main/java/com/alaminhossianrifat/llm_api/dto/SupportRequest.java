package com.alaminhossianrifat.llm_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SupportRequest {

    @NotBlank(message = "Text cannot be blank")
    @Size(min = 1, max = 2000, message = "Text length must be between 1 and 2000 characters")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}