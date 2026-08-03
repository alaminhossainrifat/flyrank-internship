package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Submission;
import com.rifat.widget_platform_backend.repository.SubmissionRepository;
import com.rifat.widget_platform_backend.service.GeoEnrichmentService;
import com.rifat.widget_platform_backend.service.NotificationService;
import com.rifat.widget_platform_backend.service.WidgetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionRepository submissionRepository;
    private final WidgetService widgetService;
    private final GeoEnrichmentService geoEnrichmentService;
    private final NotificationService notificationService; // Injected notification service

    @PostMapping
    public ResponseEntity<?> submitForm(
            @RequestBody Map<String, Object> payload,
            @RequestParam UUID widgetId,
            HttpServletRequest request) {

        // 1. Oversize Payload Validation (Reject if payload is too large, e.g., > 2000 chars)
        if (payload.toString().length() > 2000) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE) // 413 Status
                    .body(Map.of("error", "Payload size exceeds the maximum limit"));
        }

        // 2. Empty Payload Validation
        if (payload.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400 Status
                    .body(Map.of("error", "Submission payload cannot be empty"));
        }

        // 3. Honeypot spam protection check
        if (payload.containsKey("_bot_check") && !payload.get("_bot_check").toString().isEmpty()) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Submission received"));
        }

        // Extract client IP address
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        // Fetch location data using the IP address
        Map<String, Object> geoData = geoEnrichmentService.getGeoData(ipAddress);

        Submission submission = new Submission();
        var widget = widgetService.getWidgetById(widgetId); // Store widget reference

        submission.setWidget(widget);
        submission.setPayload(payload);
        submission.setIpAddress(ipAddress);
        submission.setGeoLocation(geoData);

        // 1. Main Path: Save submission to the database
        Submission savedSubmission = submissionRepository.save(submission);

        // 2. Safe Side Effect: Trigger email notification asynchronously or handled safely
        notificationService.sendEmailNotification(widget.getName());

        return ResponseEntity.ok(savedSubmission);
    }
}