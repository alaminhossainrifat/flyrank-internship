package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Submission;
import com.rifat.widget_platform_backend.repository.SubmissionRepository;
import com.rifat.widget_platform_backend.service.GeoEnrichmentService;
import com.rifat.widget_platform_backend.service.WidgetService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<?> submitForm(
            @RequestBody Map<String, Object> payload,
            @RequestParam UUID widgetId,
            HttpServletRequest request) { // HttpServletRequest added to capture IP address

        // 1. Honeypot Spam Protection
        if (payload.containsKey("_bot_check") && !payload.get("_bot_check").toString().isEmpty()) {
            return ResponseEntity.ok(Map.of("status", "success", "message", "Submission received"));
        }

        // 2. IP Address Find
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        // 3. Call Geo Enrichment
        Map<String, Object> geoData = geoEnrichmentService.getGeoData(ipAddress);

        // 4. Data Save
        Submission submission = new Submission();
        submission.setWidget(widgetService.getWidgetById(widgetId));
        submission.setPayload(payload);
        submission.setIpAddress(ipAddress);
        submission.setGeoLocation(geoData); // Location data saved (no problem even if null)

        return ResponseEntity.ok(submissionRepository.save(submission));
    }
}
