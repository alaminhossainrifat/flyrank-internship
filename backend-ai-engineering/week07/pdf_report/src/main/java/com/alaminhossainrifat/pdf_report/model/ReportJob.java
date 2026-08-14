package com.alaminhossainrifat.pdf_report.model;

import jakarta.persistence.*;
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

    @Column(length = 1000)
    private String failureReason;
}