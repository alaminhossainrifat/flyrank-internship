package com.alaminhossainrifat.pdf_report.repository;

import com.alaminhossainrifat.pdf_report.model.ReportJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportJobRepository extends JpaRepository<ReportJob, Long> {
    Optional<ReportJob> findByJobId(String jobId);
    List<ReportJob> findAllByOrderByCreatedAtDesc();
}