package com.alaminhossainrifat.api_endpoint.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StatusResponse {

    private String status;
    private Instant timestamp;

    public StatusResponse() {
    }

    public StatusResponse(String status, Instant timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
}
