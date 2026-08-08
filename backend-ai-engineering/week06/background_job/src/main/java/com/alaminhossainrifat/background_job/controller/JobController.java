package com.alaminhossainrifat.background_job.controller;

import com.alaminhossainrifat.background_job.config.AiWorkerService;
import com.alaminhossainrifat.background_job.entity.AiJob;
import com.alaminhossainrifat.background_job.repoaitory.AiJobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> startJob() {
        // Create a new job with PENDING status
        AiJob job = new AiJob();
        job.setStatus("PENDING");
        job = repository.save(job);

        // Trigger the background worker asynchronously
        workerService.processAiTask(job.getId());

        // Return 202 Accepted immediately with the Job ID
        return ResponseEntity.status(202).body(job.getId().toString());
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<AiJob> getJobStatus(@PathVariable UUID id) {
        // Fetch and return the job status from the database
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}