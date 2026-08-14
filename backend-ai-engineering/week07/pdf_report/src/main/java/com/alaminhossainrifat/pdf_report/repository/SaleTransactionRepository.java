package com.alaminhossainrifat.pdf_report.repository;

import com.alaminhossainrifat.pdf_report.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {

    @Query("SELECT s.serviceName, COUNT(s), SUM(s.amount) FROM SaleTransaction s GROUP BY s.serviceName")
    List<Object[]> getSalesSummaryByService();
}