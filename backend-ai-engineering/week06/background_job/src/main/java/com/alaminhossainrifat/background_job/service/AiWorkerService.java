package com.alaminhossainrifat.background_job.service;

import com.alaminhossainrifat.background_job.entity.AiJob;
import com.alaminhossainrifat.background_job.repoaitory.AiJobRepository;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AiWorkerService {

    private final AiJobRepository repository;

    public AiWorkerService(AiJobRepository repository) {
        this.repository = repository;
    }

    @Async("aiTaskExecutor")
    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void processAiTask(UUID jobId) {
        AiJob job = repository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        try {
            job.setStatus("PROCESSING");
            repository.save(job);

            // Simulate slow operation
            Thread.sleep(5000);

            // Simulating a random failure for testing retries
            if (Math.random() > 0.5) {
                throw new RuntimeException("Temporary AI Service Unavailable");
            }

            String aiResponse = "Mock AI Response: Operation Completed Successfully";
            job.setStatus("COMPLETED");
            job.setResult(aiResponse);
            repository.save(job);

        } catch (Exception e) {
            // Log the error state and rethrow to trigger Spring Retry
            job.setStatus("FAILED");
            job.setResult("Error: " + e.getMessage());
            repository.save(job);
            throw new RuntimeException(e);
        }
    }
}