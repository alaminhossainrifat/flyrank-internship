package com.alaminhossainrifat.background_job.config;

import com.alaminhossainrifat.background_job.entity.AiJob;
import com.alaminhossainrifat.background_job.repoaitory.AiJobRepository;
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
    public void processAiTask(UUID jobId) {
        // Fetch the pending job from database
        AiJob job = repository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        try {
            // Update status to processing
            job.setStatus("PROCESSING");
            repository.save(job);

            // Simulate slow AI operation (e.g., A6 AI Call)
            Thread.sleep(10000);
            String aiResponse = "Mock AI Response: Operation Completed Successfully";

            // Update status to completed
            job.setStatus("COMPLETED");
            job.setResult(aiResponse);
            repository.save(job);

        } catch (Exception e) {
            // Update status to failed if any error occurs
            job.setStatus("FAILED");
            job.setResult("Error: " + e.getMessage());
            repository.save(job);
        }
    }
}