package com.alaminhossainrifat.pdf_report.service;

import com.alaminhossainrifat.pdf_report.model.ReportJob;
import com.alaminhossainrifat.pdf_report.repository.ReportJobRepository;
import com.alaminhossainrifat.pdf_report.repository.SaleTransactionRepository;
import com.alaminhossainrifat.pdf_report.util.PdfGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportJobRepository reportJobRepository;
    private final SaleTransactionRepository saleTransactionRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;

    @Value("${storage.report.dir}")
    private String storageDir;

    public ReportJob createJob() {
        ReportJob job = ReportJob.builder()
                .jobId(UUID.randomUUID().toString())
                .status(ReportJob.JobStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        ReportJob saved = reportJobRepository.save(job);
        processReport(saved.getJobId());
        return saved;
    }

    @Async("reportTaskExecutor")
    public void processReport(String jobId) {
        ReportJob job = reportJobRepository.findByJobId(jobId)
                .orElseThrow(() -> new IllegalStateException("Job not found: " + jobId));

        job.setStatus(ReportJob.JobStatus.PROCESSING);
        reportJobRepository.save(job);

        try {
            List<Object[]> summaryData = saleTransactionRepository.getSalesSummaryByService();
            File pdfFile = pdfGeneratorUtil.generateSalesSummaryPdf(summaryData, storageDir, jobId);

            job.setFilePath(pdfFile.getAbsolutePath());
            job.setDownloadUrl("/api/reports/" + jobId + "/download");
            job.setStatus(ReportJob.JobStatus.COMPLETED);
        } catch (Exception e) {
            job.setStatus(ReportJob.JobStatus.FAILED);
            job.setFailureReason(e.getMessage());
        }

        job.setCompletedAt(LocalDateTime.now());
        reportJobRepository.save(job);
    }

    public ReportJob getJobStatus(String jobId) {
        return reportJobRepository.findByJobId(jobId)
                .orElseThrow(() -> new IllegalStateException("Job not found: " + jobId));
    }

    public List<ReportJob> getAllJobs() {
        return reportJobRepository.findAllByOrderByCreatedAtDesc();
    }

    @Scheduled(cron = "${report.schedule.cron}")
    public void scheduledReportGeneration() {
        createJob();
    }
}