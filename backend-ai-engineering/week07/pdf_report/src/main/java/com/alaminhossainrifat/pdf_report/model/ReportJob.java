package com.alaminhossainrifat.pdf_report.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "report_jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    private String downloadUrl;

    private String filePath;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    public enum JobStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}