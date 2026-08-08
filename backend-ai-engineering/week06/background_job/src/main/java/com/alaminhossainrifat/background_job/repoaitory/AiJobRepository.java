package com.alaminhossainrifat.background_job.repoaitory;

import com.alaminhossainrifat.background_job.entity.AiJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface AiJobRepository extends JpaRepository<AiJob, UUID> {
    Optional<AiJob> findByIdempotencyKey(String idempotencyKey);
}