# 🚀 FlyRank Backend AI Engineering – Week 7 (PDF Report Generator)

<div align="center">

# PDF Report Generator

Build an asynchronous PDF reporting pipeline that queries database data, generates a PDF report in the background, stores the generated artifact, and provides status tracking and download access.

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.0-brightgreen)
![H2](https://img.shields.io/badge/Database-H2-blue)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data-JPA-success)
![OpenPDF](https://img.shields.io/badge/PDF-OpenPDF-4B5563)
![Maven](https://img.shields.io/badge/Build-Maven-red)
![Async](https://img.shields.io/badge/Processing-Async-purple)
![Scheduling](https://img.shields.io/badge/Scheduling-Cron-orange)
![License](https://img.shields.io/badge/License-Educational-lightgrey)

</div>

---

# 📖 Overview

This project was developed as part of the **FlyRank Backend AI Engineering Internship – Week 7** assignment.

The objective of this assignment is to build a complete **PDF report generation pipeline** that can:

- Query data from a database.
- Perform SQL aggregation.
- Generate a PDF report.
- Process report generation as a background job.
- Track the job status.
- Store the generated PDF outside the database.
- Provide a download endpoint.
- Generate reports on demand.
- Generate reports automatically on a configurable schedule.

The assignment focuses on combining **SQL aggregation**, **artifact handling**, and the **A7 background job pattern** into one practical backend feature.

---

# 🎯 Assignment Goal

The main goal is to build a backend pipeline where a report request does not block the client while the PDF is being generated.

The workflow is:

```text
Client
   │
   ▼
POST /api/reports/generate
   │
   ▼
Create Report Job
   │
   ▼
PENDING
   │
   ▼
Background Worker
   │
   ▼
PROCESSING
   │
   ├── Query Database
   │
   ├── SQL Aggregation
   │
   ├── Generate PDF
   │
   └── Store PDF File
   │
   ▼
COMPLETED
   │
   ▼
Download Report
````

If report generation fails:

```text
PROCESSING
    │
    ▼
Exception
    │
    ▼
FAILED
    │
    ▼
Failure Reason Stored
```

---

# ✨ Features

* ✅ RESTful Report Generation API
* ✅ SQL Aggregation using JPQL
* ✅ PDF Generation using OpenPDF
* ✅ Asynchronous Background Job Processing
* ✅ Job Status Tracking
* ✅ `PENDING → PROCESSING → COMPLETED/FAILED` Lifecycle
* ✅ On-Demand Report Generation
* ✅ PDF Download Endpoint
* ✅ Report History Endpoint
* ✅ Failure Reason Tracking
* ✅ Proper `404 Not Found` Handling
* ✅ Local PDF Artifact Storage
* ✅ Generated Reports Excluded from Git
* ✅ Configurable Scheduled Report Generation
* ✅ Automatic Test Data Initialization
* ✅ H2 Database
* ✅ Spring Data JPA
* ✅ Maven Build System

---

# 🛠️ Technology Stack

| Technology        | Version / Purpose     |
| ----------------- | --------------------- |
| Java              | 17                    |
| Spring Boot       | 3.2.5                 |
| Spring Web        | REST API              |
| Spring Data JPA   | Database Access       |
| Hibernate         | ORM                   |
| H2 Database       | In-Memory Database    |
| OpenPDF           | PDF Generation        |
| Spring Async      | Background Processing |
| Spring Scheduling | Scheduled Jobs        |
| Lombok            | Boilerplate Reduction |
| Maven             | Build Tool            |
| Postman           | API Testing           |
| Git               | Version Control       |
| GitHub            | Repository Hosting    |

---

# 🏗️ Architecture

The application follows a layered backend architecture.

```text
                    ┌─────────────────────┐
                    │       Client        │
                    │      Postman        │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ ReportController    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │   ReportService     │
                    └──────────┬──────────┘
                               │
                 ┌─────────────┴─────────────┐
                 │                           │
                 ▼                           ▼
      ┌─────────────────────┐     ┌─────────────────────┐
      │ SaleTransaction      │     │ ReportJob           │
      │ Repository           │     │ Repository          │
      └──────────┬──────────┘     └──────────┬──────────┘
                 │                           │
                 ▼                           │
      ┌─────────────────────┐                │
      │ SQL Aggregation     │                │
      └──────────┬──────────┘                │
                 │                           │
                 ▼                           │
      ┌─────────────────────┐                │
      │ PdfGeneratorUtil    │                │
      │ OpenPDF             │                │
      └──────────┬──────────┘                │
                 │                           │
                 ▼                           ▼
      ┌─────────────────────┐     ┌─────────────────────┐
      │ generated-reports/  │     │ Report Job Status   │
      │ PDF Artifact        │     │ + File Path + URL   │
      └─────────────────────┘     └─────────────────────┘
```

---

# 📂 Project Structure

```text
pdf_report/
│
├── generated-reports/
│   └── Generated PDF files
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── alaminhossainrifat/
│   │   │           └── pdf_report/
│   │   │               │
│   │   │               ├── config/
│   │   │               │   ├── AsyncConfig.java
│   │   │               │   └── DataInitializer.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── ReportController.java
│   │   │               │
│   │   │               ├── model/
│   │   │               │   ├── ReportJob.java
│   │   │               │   └── SaleTransaction.java
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── ReportJobRepository.java
│   │   │               │   └── SaleTransactionRepository.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── ReportService.java
│   │   │               │
│   │   │               ├── util/
│   │   │               │   └── PdfGeneratorUtil.java
│   │   │               │
│   │   │               └── PdfReportApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── alaminhossainrifat/
│                   └── pdf_report/
│                       └── PdfReportApplicationTests.java
│
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

---

# 🗄️ Database

The project uses an **H2 in-memory database** for local development and testing.

The database stores two main entities:

```text
SaleTransaction
        │
        ├── id
        ├── clientName
        ├── serviceName
        ├── amount
        └── transactionDate


ReportJob
        │
        ├── id
        ├── jobId
        ├── status
        ├── downloadUrl
        ├── filePath
        ├── failureReason
        ├── createdAt
        └── completedAt
```

---

# 📊 Sale Transaction Data

The application initializes sample sales data when it starts.

Example services include:

```text
Cloud Architecture
Backend Development
AI Pipeline Setup
Code Review
Security Audit
```

The data is used for SQL aggregation before generating the PDF report.

---

# 🔎 SQL Aggregation

The application performs aggregation directly at the database query level.

```java
@Query("""
    SELECT s.serviceName, COUNT(s), SUM(s.amount)
    FROM SaleTransaction s
    GROUP BY s.serviceName
""")
List<Object[]> getSalesSummaryByService();
```

This query groups transactions by service and calculates:

* Service name
* Number of transactions
* Total amount

Example result:

```text
Service                  Count       Total
------------------------------------------------
Cloud Architecture        1          1200.00
Backend Development       1          2500.00
AI Pipeline Setup         1          3200.00
Code Review               1           800.00
Security Audit            1          1900.00
```

---

# 📄 PDF Generation

The aggregated data is rendered into a PDF using **OpenPDF**.

The PDF report contains:

* Report title
* Service summary
* Transaction count
* Total amount
* Tabular report data
* Summary information

Generated PDF files are stored inside:

```text
generated-reports/
```

Example:

```text
generated-reports/
└── report-04ad9d1f-4156-450a-b856-f42c37d8eb0f.pdf
```

---

# ⚙️ Background Job Processing

The report generation process uses Spring's asynchronous processing.

The initial API request creates a job and returns immediately instead of waiting for the PDF generation to finish.

```text
POST /api/reports/generate
          │
          ▼
     Create Job
          │
          ▼
      PENDING
          │
          ▼
    @Async Worker
          │
          ▼
     PROCESSING
          │
          ▼
   Generate PDF
          │
          ▼
     COMPLETED
```

The application uses a dedicated task executor:

```text
Core Pool Size:    2
Maximum Pool Size: 5
Queue Capacity:    50
Thread Prefix:     ReportJob-
```

---

# 🔄 Job Status Lifecycle

Every report follows a controlled lifecycle.

```text
PENDING
   │
   ▼
PROCESSING
   │
   ├──────────────► COMPLETED
   │
   └──────────────► FAILED
```

### `PENDING`

The job has been created but background processing has not started yet.

### `PROCESSING`

The background worker is currently generating the report.

### `COMPLETED`

The PDF has been successfully generated and stored.

### `FAILED`

An error occurred during report generation.

The failure reason is stored with the job.

---

# 📦 Artifact Handling

Generated PDF files are **not stored inside the database**.

Instead:

```text
Database
   │
   ├── Job ID
   ├── Status
   ├── File Path
   ├── Download URL
   └── Failure Reason
```

The actual PDF is stored separately:

```text
generated-reports/
   │
   └── report-{jobId}.pdf
```

This follows the assignment requirement to **store and link artifacts instead of passing large PDF files through the application workflow**.

---

# 🌐 REST API

Base URL:

```text
http://localhost:8080
```

| Method | Endpoint                        | Description                      |
| ------ | ------------------------------- | -------------------------------- |
| POST   | `/api/reports/generate`         | Generate a report asynchronously |
| GET    | `/api/reports/{jobId}/status`   | Check report job status          |
| GET    | `/api/reports/{jobId}/download` | Download completed PDF           |
| GET    | `/api/reports`                  | Get report history               |

---

# 📨 Generate Report

## Request

```http
POST /api/reports/generate
```

The endpoint creates a new background job.

Expected response:

```text
HTTP 202 Accepted
```

Example:

```json
{
  "jobId": "04ad9d1f-4156-450a-b856-f42c37d8eb0f",
  "status": "PENDING"
}
```

---

# 🔍 Check Report Status

## Request

```http
GET /api/reports/{jobId}/status
```

Example:

```http
GET /api/reports/04ad9d1f-4156-450a-b856-f42c37d8eb0f/status
```

Possible statuses:

```text
PENDING
PROCESSING
COMPLETED
FAILED
```

Example completed response:

```json
{
  "jobId": "04ad9d1f-4156-450a-b856-f42c37d8eb0f",
  "status": "COMPLETED",
  "downloadUrl": "/api/reports/04ad9d1f-4156-450a-b856-f42c37d8eb0f/download"
}
```

---

# 📥 Download Report

## Request

```http
GET /api/reports/{jobId}/download
```

Example:

```http
GET /api/reports/04ad9d1f-4156-450a-b856-f42c37d8eb0f/download
```

### Possible Responses

```text
200 OK
```

The PDF file is returned.

If the report is not completed:

```text
409 Conflict
```

If the job or file does not exist:

```text
404 Not Found
```

---

# 📚 Report History

## Request

```http
GET /api/reports
```

This endpoint returns previously created report jobs ordered by creation date.

It can be used to monitor:

* Pending reports
* Processing reports
* Completed reports
* Failed reports

---

# ❌ Error Handling

The application includes explicit error handling for invalid jobs and failed report generation.

## Invalid Job ID

```http
GET /api/reports/00000000-0000-0000-0000-000000000000/status
```

Expected response:

```text
HTTP 404 Not Found
```

---

## Failed Report

When report generation throws an exception:

```text
Status → FAILED
Failure Reason → Stored
Completed At → Recorded
```

Example:

```json
{
  "status": "FAILED",
  "failureReason": "Unable to generate PDF"
}
```

---

# ⏰ Scheduled Report Generation

Scheduled report generation was implemented as the **stretch requirement**.

The application uses Spring's:

```java
@Scheduled(cron = "${report.schedule.cron}")
```

The schedule is configured through:

```properties
report.schedule.cron=0 0 * * * *
```

The default configuration generates a report every hour.

---

# 🧪 Testing Scheduled Generation

For temporary testing, the cron expression can be changed to:

```properties
report.schedule.cron=0 */2 * * * *
```

This generates a report every two minutes.

After restarting the application:

1. Wait for the scheduler to execute.
2. Call:

```http
GET /api/reports
```

3. Verify that a new report job was created automatically.
4. Restore the desired cron expression after testing.

---

# ⚙️ Configuration

The main configuration is stored in:

```text
src/main/resources/application.properties
```

Example:

```properties
server.port=8080

spring.datasource.url=jdbc:h2:mem:reportdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

storage.report.dir=./generated-reports

report.schedule.cron=0 0 * * * *
```

---

# ▶️ Running the Project

## Prerequisites

Make sure the following are installed:

* Java 17 or higher
* Maven
* Git
* Postman
* IntelliJ IDEA or another Java IDE

---

## Clone Repository

```bash
git clone https://github.com/alaminhossainrifat/flyrank-internship.git
```

Move into the project directory:

```bash
cd flyrank-internship/backend-ai-engineering/week07/pdf_report
```

---

## Run with Maven

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
./mvnw spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# 🧪 Testing with Postman

## 1. Generate Report

```http
POST http://localhost:8080/api/reports/generate
```

Copy the returned `jobId`.

---

## 2. Check Status

```http
GET http://localhost:8080/api/reports/{jobId}/status
```

Keep checking until:

```text
COMPLETED
```

---

## 3. Download PDF

```http
GET http://localhost:8080/api/reports/{jobId}/download
```

Verify that the generated PDF opens successfully.

---

## 4. Check Report History

```http
GET http://localhost:8080/api/reports
```

Verify that the generated report appears in the job history.

---

## 5. Test Invalid Job ID

```http
GET http://localhost:8080/api/reports/00000000-0000-0000-0000-000000000000/status
```

Expected:

```text
404 Not Found
```

---

# 🔐 Git Configuration

Generated PDF files should not be committed to GitHub.

The following entry is included in `.gitignore`:

```gitignore
generated-reports/
```

This ensures that generated report artifacts remain local and are not pushed to the repository.

---

# 🔄 Development Workflow

The project was completed incrementally:

```text
Requirement Analysis
        │
        ▼
Spring Boot Project Setup
        │
        ▼
Database Models
        │
        ▼
Repositories
        │
        ▼
SQL Aggregation
        │
        ▼
Async Configuration
        │
        ▼
PDF Generation
        │
        ▼
Background Job Processing
        │
        ▼
REST API
        │
        ▼
Artifact Storage
        │
        ▼
Report History
        │
        ▼
Error Handling
        │
        ▼
Scheduled Generation
        │
        ▼
Postman Testing
        │
        ▼
GitHub Push
```

Each stage was tested successfully before proceeding to the next stage.

---

# 📋 Assignment Requirements Checklist

## Core Requirements

* [x] Query application data
* [x] SQL aggregation
* [x] Render data into PDF
* [x] Background job processing
* [x] On-demand report generation
* [x] Job status tracking
* [x] Artifact storage
* [x] Store and link PDF instead of passing large files directly

## Additional Improvements

* [x] Report history endpoint
* [x] Failure reason tracking
* [x] Proper 404 error handling
* [x] Generated reports excluded from Git
* [x] Postman API testing

## Stretch Requirement

* [x] Scheduled report generation
* [x] Configurable cron expression

---

# 🧠 Key Concepts Demonstrated

This assignment demonstrates practical backend concepts including:

* Spring Boot REST APIs
* Spring Data JPA
* JPQL
* SQL aggregation
* Asynchronous processing
* Thread pool configuration
* Background job architecture
* PDF generation
* File artifact management
* Job lifecycle management
* Exception handling
* Scheduled tasks
* Cron expressions
* Database initialization
* REST API testing

---

# 📚 What I Learned

Through this assignment I learned how to build a complete asynchronous reporting pipeline from database query to downloadable PDF artifact.

Key learning outcomes:

* How to perform database-level aggregation.
* How to generate PDFs using OpenPDF.
* How to execute long-running tasks asynchronously.
* How to track background jobs using database records.
* How to handle job states such as `PENDING`, `PROCESSING`, `COMPLETED`, and `FAILED`.
* How to store generated artifacts outside the database.
* How to expose a download endpoint for generated files.
* How to implement scheduled background tasks using Spring Scheduling.
* How to test asynchronous APIs using Postman.
* How to keep generated artifacts out of Git version control.

---

# 🚀 Future Improvements

Possible improvements for a production-ready version include:

* PostgreSQL or MySQL integration
* Amazon S3 or cloud object storage
* RabbitMQ or Kafka based job queues
* Authentication and authorization
* Retry mechanism for failed jobs
* Idempotency protection
* Report filtering by date range
* Pagination for report history
* PDF report templates
* Automated report cleanup
* Docker and Docker Compose
* Swagger / OpenAPI documentation
* Unit and integration test expansion
* Monitoring and logging
* Production-grade scheduling with distributed job locking

---

# 📊 Project Status

```text
┌─────────────────────────────────────────────┐
│              PROJECT STATUS                 │
├─────────────────────────────────────────────┤
│ SQL Aggregation              ✅ Completed   │
│ PDF Generation               ✅ Completed   │
│ Async Background Jobs        ✅ Completed   │
│ Artifact Storage             ✅ Completed   │
│ Job Status Tracking          ✅ Completed   │
│ Report History               ✅ Completed   │
│ Error Handling               ✅ Completed   │
│ Scheduled Generation         ✅ Completed   │
│ Postman Testing              ✅ Passed      │
│ GitHub Push                  ✅ Completed   │
└─────────────────────────────────────────────┘
```

**Status: Completed**

The complete PDF report generation pipeline has been implemented, tested, and completed according to the Week 7 assignment requirements.

---

# 🔗 Repository

GitHub Repository:

https://github.com/alaminhossainrifat/flyrank-internship/tree/main/backend-ai-engineering/week07/pdf_report

---

# 👨‍💻 Author

**Al Amin Hossain Rifat**

Backend AI Engineering Intern

**FlyRank AI Internship Program**

GitHub:

https://github.com/alaminhossainrifat

---

# ⭐ Acknowledgement

Developed as part of the **FlyRank Backend AI Engineering Internship – Week 7** assignment.

The project demonstrates a complete backend reporting workflow using **Java, Spring Boot, Spring Data JPA, H2, OpenPDF, Spring Async, and Spring Scheduling**.

```
```
