package com.alaminhossainrifat.background_job.controller;

import com.alaminhossainrifat.background_job.service.AiWorkerService;
import com.alaminhossainrifat.background_job.entity.AiJob;
import com.alaminhossainrifat.background_job.repoaitory.AiJobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final AiJobRepository repository;
    private final AiWorkerService workerService;

    public JobController(AiJobRepository repository, AiWorkerService workerService) {
        this.repository = repository;
        this.workerService = workerService;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startJob(@RequestHeader("Idempotency-Key") String idempotencyKey) {
        // Check if a job with this key already exists
        Optional<AiJob> existingJob = repository.findByIdempotencyKey(idempotencyKey);
        if (existingJob.isPresent()) {
            // Return the existing Job ID to prevent duplicate processing
            return ResponseEntity.status(202).body(existingJob.get().getId().toString());
        }

        // Create a new job
        AiJob job = new AiJob();
        job.setIdempotencyKey(idempotencyKey);
        job.setStatus("PENDING");
        job = repository.save(job);

        // Trigger worker
        workerService.processAiTask(job.getId());

        return ResponseEntity.status(202).body(job.getId().toString());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<AiJob> getJobStatus(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}