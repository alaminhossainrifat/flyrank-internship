package com.alaminhossainrifat.api_endpoint.dto;

import lombok.Data;

@Data
public class HelloResponse {

    private String message;

    public HelloResponse(){

    }

    public HelloResponse(String message) {
        this.message = message;
    }
}
