package com.alaminhossainrifat.api_endpoint.controller;

import com.alaminhossainrifat.api_endpoint.dto.HelloResponse;
import com.alaminhossainrifat.api_endpoint.dto.StatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public HelloResponse sayHello() {
        return new HelloResponse("Hello from FlyRank!");
    }

    @GetMapping("/api/status")
    public StatusResponse getStatus() {
        return new StatusResponse("UP", Instant.now());
    }
}