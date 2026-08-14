package com.alaminhossainrifat.pdf_report.util;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PdfGeneratorUtil {

    public File generateSalesSummaryPdf(List<Object[]> summaryData, String outputDir, String jobId) throws Exception {
        File dir = new File(outputDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = "report-" + jobId + ".pdf";
        File file = new File(dir, fileName);

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font metaFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        Paragraph title = new Paragraph("Sales Summary Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        String generatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Paragraph meta = new Paragraph("Generated at: " + generatedAt, metaFont);
        meta.setAlignment(Element.ALIGN_CENTER);
        meta.setSpacingAfter(20);
        document.add(meta);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{3, 2, 2});

        String[] headers = {"Service Name", "Transaction Count", "Total Amount"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
            cell.setPadding(6);
            table.addCell(cell);
        }

        double grandTotal = 0.0;
        for (Object[] row : summaryData) {
            String serviceName = String.valueOf(row[0]);
            long count = ((Number) row[1]).longValue();
            double amount = ((Number) row[2]).doubleValue();
            grandTotal += amount;

            table.addCell(new PdfPCell(new Paragraph(serviceName, cellFont)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(count), cellFont)));
            table.addCell(new PdfPCell(new Paragraph(String.format("%.2f", amount), cellFont)));
        }

        document.add(table);

        Paragraph totalPara = new Paragraph("Grand Total: " + String.format("%.2f", grandTotal), headerFont);
        totalPara.setSpacingBefore(15);
        totalPara.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalPara);

        document.close();

        return file;
    }
}