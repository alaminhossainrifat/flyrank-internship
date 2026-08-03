package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Submission;
import com.rifat.widget_platform_backend.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final SubmissionRepository submissionRepository;

    // 1. Get all submissions for a specific widget
    @GetMapping("/widgets/{widgetId}/submissions")
    public ResponseEntity<List<Submission>> getSubmissions(@PathVariable UUID widgetId) {
        List<Submission> submissions = submissionRepository.findByWidgetId(widgetId);
        return ResponseEntity.ok(submissions);
    }

    // 2. Get basic analytics and geo-breakdown for a widget
    @GetMapping("/widgets/{widgetId}/stats")
    public ResponseEntity<Map<String, Object>> getWidgetStats(@PathVariable UUID widgetId) {
        List<Submission> submissions = submissionRepository.findByWidgetId(widgetId);

        int totalSubmissions = submissions.size();

        // Extract country data from JSONB geoLocation field and count them
        Map<String, Long> submissionsByCountry = submissions.stream()
                .filter(sub -> sub.getGeoLocation() != null && sub.getGeoLocation().containsKey("country"))
                .collect(Collectors.groupingBy(
                        sub -> sub.getGeoLocation().get("country").toString(),
                        Collectors.counting()
                ));

        Map<String, Object> stats = Map.of(
                "totalSubmissions", totalSubmissions,
                "submissionsByCountry", submissionsByCountry
        );

        return ResponseEntity.ok(stats);
    }
}