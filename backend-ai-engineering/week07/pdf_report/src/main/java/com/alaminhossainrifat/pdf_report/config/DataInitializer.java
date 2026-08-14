package com.alaminhossainrifat.pdf_report.config;

import com.alaminhossainrifat.pdf_report.model.SaleTransaction;
import com.alaminhossainrifat.pdf_report.repository.SaleTransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(SaleTransactionRepository repository) {
        return args -> {
            List<SaleTransaction> sampleData = List.of(
                    SaleTransaction.builder().clientName("Client A").serviceName("Cloud Architecture").amount(1200.00).transactionDate(LocalDate.now().minusDays(5)).build(),
                    SaleTransaction.builder().clientName("Client B").serviceName("Backend Development").amount(2500.00).transactionDate(LocalDate.now().minusDays(3)).build(),
                    SaleTransaction.builder().clientName("Client C").serviceName("AI Pipeline Setup").amount(3200.00).transactionDate(LocalDate.now().minusDays(2)).build(),
                    SaleTransaction.builder().clientName("Client D").serviceName("Code Review").amount(800.00).transactionDate(LocalDate.now().minusDays(1)).build(),
                    SaleTransaction.builder().clientName("Client E").serviceName("Security Audit").amount(1900.00).transactionDate(LocalDate.now()).build()
            );
            repository.saveAll(sampleData);
        };
    }
}