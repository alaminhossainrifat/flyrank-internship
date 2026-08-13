package com.alaminhossianrifat.llm_api.dto;

public class SupportResponse {
    private String category;
    private String urgency;
    private double confidence;
    private String reason;

    public SupportResponse() {
    }

    public SupportResponse(String category, String urgency, double confidence, String reason) {
        this.category = category;
        this.urgency = urgency;
        this.confidence = confidence;
        this.reason = reason;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}