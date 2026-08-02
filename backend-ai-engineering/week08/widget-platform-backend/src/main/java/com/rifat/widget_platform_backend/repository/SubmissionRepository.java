package com.rifat.widget_platform_backend.repository;

import com.rifat.widget_platform_backend.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
    List<Submission> findByWidgetId(UUID widgetId);
}
