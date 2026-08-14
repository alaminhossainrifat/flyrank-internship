package com.alaminhossainrifat.pdf_report.controller;

import com.alaminhossainrifat.pdf_report.model.ReportJob;
import com.alaminhossainrifat.pdf_report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/generate")
    public ResponseEntity<ReportJob> generateReport() {
        ReportJob job = reportService.createJob();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(job);
    }

    @GetMapping("/{jobId}/status")
    public ResponseEntity<ReportJob> getStatus(@PathVariable String jobId) {
        ReportJob job = reportService.getJobStatus(jobId);
        return ResponseEntity.ok(job);
    }

    @GetMapping("/{jobId}/download")
    public ResponseEntity<Resource> downloadReport(@PathVariable String jobId) {
        ReportJob job = reportService.getJobStatus(jobId);

        if (job.getStatus() != ReportJob.JobStatus.COMPLETED || job.getFilePath() == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        File file = new File(job.getFilePath());
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Resource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<List<ReportJob>> getAllReports() {
        List<ReportJob> jobs = reportService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
}