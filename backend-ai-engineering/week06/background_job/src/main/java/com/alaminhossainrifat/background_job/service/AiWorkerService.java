package com.alaminhossainrifat.background_job.service;

import com.alaminhossainrifat.background_job.entity.AiJob;
import com.alaminhossainrifat.background_job.repoaitory.AiJobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AiWorkerService {

    private static final Logger logger = LoggerFactory.getLogger(AiWorkerService.class);
    private final AiJobRepository repository;

    public AiWorkerService(AiJobRepository repository) {
        this.repository = repository;
    }

    @Async("aiTaskExecutor")
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void processAiTask(UUID jobId) { // Removed 'throws Exception'
        AiJob job = repository.findById(jobId).orElseThrow(() -> new RuntimeException("Job not found"));

        job.setStatus("PROCESSING");
        repository.save(job);

        // Simulate slow operation with internal try-catch
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during sleep", e);
        }

        // Forcing failure for testing recovery (Change this to true to test)
        if (Math.random() > 0.5) {
//        if (true) {
            logger.warn("Task failed for Job ID: {}. Retrying...", jobId);
            throw new RuntimeException("Temporary AI Service Unavailable");
        }

        String aiResponse = "Mock AI Response: Operation Completed Successfully";
        job.setStatus("COMPLETED");
        job.setResult(aiResponse);
        repository.save(job);

        logger.info("Job ID: {} completed successfully.", jobId);
    }

    // This method is called when all retries are exhausted
    @Recover
    public void recover(Exception e, UUID jobId) {
        logger.error("CRITICAL ALERT: All retries failed for Job ID: {}. Initiating recovery...", jobId);

        repository.findById(jobId).ifPresent(job -> {
            job.setStatus("FAILED");
            job.setResult("Fatal Error: " + e.getMessage());
            repository.save(job);

            // Triggering external alert (e.g., Slack Webhook, Email, or Server Log Monitor)
            sendAlertNotification(job);
        });
    }

    private void sendAlertNotification(AiJob job) {
        // In a production environment, this would call a third-party API
        logger.error("SYSTEM ALERT NOTIFICATION: Background job {} failed permanently. Check server-level logs for deeper analysis.", job.getId());
    }
}