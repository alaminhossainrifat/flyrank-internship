package com.rifat.widget_platform_backend.controller;

import com.rifat.widget_platform_backend.entity.Submission;
import com.rifat.widget_platform_backend.repository.SubmissionRepository;
import com.rifat.widget_platform_backend.service.WidgetService;
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

    @PostMapping
    public ResponseEntity<Submission> submitForm(
            @RequestBody Map<String, Object> payload,
            @RequestParam UUID widgetId) {

        // Basic saving logic. CORS, validation, and rate limiting will be applied later.
        Submission submission = new Submission();
        submission.setWidget(widgetService.getWidgetById(widgetId));
        submission.setPayload(payload);

        return ResponseEntity.ok(submissionRepository.save(submission));
    }
}
