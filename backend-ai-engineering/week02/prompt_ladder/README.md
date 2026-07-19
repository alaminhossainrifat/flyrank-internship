# 🚀 Prompt Ladder – Backend Task Manager REST API

> A practical demonstration of how **prompt engineering** progressively improves AI-generated backend code through iterative refinement. Starting from a vague prompt, each version introduces clearer requirements, better constraints, and verification until the project becomes a production-ready Spring Boot REST API.

---

## 📌 Project Goal

Build a **Task Manager REST API** using **Java 25** and **Spring Boot**, while demonstrating how improving prompts leads to higher-quality software.

---

# 🛠️ Technology Stack

| Technology | Purpose |
|------------|---------|
| **Java 25** | Programming Language |
| **Spring Boot** | Backend Framework |
| **Spring Web** | REST API Development |
| **Spring Data JPA** | Data Access Layer |
| **H2 Database** | Local Development Database |
| **Jakarta Bean Validation** | Input Validation |
| **Maven** | Dependency Management |

---

# 📈 Prompt Evolution

## 🔹 Baseline – Initial Prompt

### Prompt

```text
Write backend code for a task manager.
```

### Output Summary

- In-memory List
- Only GET endpoint
- No framework specified
- No database
- No persistence

### Commit

```text
baseline: initial task manager implementation
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Initial vague prompt with no technical requirements. |
| **Improved** | Generated a minimal backend with a single GET endpoint. |
| **Still Failed** | No CRUD, no framework, no database, no persistence. |
| **Try Next** | Specify full CRUD operations. |

---

## 🔹 Version 1 – Added Clearer Goal

### Prompt

```text
Write backend code for a task manager REST API with full CRUD operations (Create, Read, Update, Delete).
```

### Commit

```text
v1: added clearer goal (CRUD)
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Added explicit CRUD requirements. |
| **Improved** | Added Create, Read, Update, and Delete operations. |
| **Still Failed** | In-memory storage, no persistence, no framework, no database. |
| **Try Next** | Specify Java, Spring Boot, Spring Data JPA, and H2 Database. |

---

## 🔹 Version 2 – Added Real Context

### Prompt

```text
Write backend code for a task manager REST API using Java, Spring Boot, Spring Data JPA, and H2 database for local development.
```

### Commit

```text
v2: added real context (Java, Spring Boot, JPA, H2)
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Added actual technology stack. |
| **Improved** | Added Entity, Repository, Service, Controller, and H2 persistence. |
| **Still Failed** | No validation, no proper HTTP status codes, no exception handling. |
| **Try Next** | Add validation, status codes, and DB portability. |

---

## 🔹 Version 3 – Added Output Format

### Prompt

```text
Respond with code only, organized by file (Entity, Repository, Service, Controller), no explanation text.
```

### Commit

```text
v3: added output format (code-only, file-by-file)
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Required file-by-file code output only. |
| **Improved** | Easier to copy into a Spring Boot project and improved workflow. |
| **Still Failed** | No functional improvement; validation and status codes still missing. |
| **Try Next** | Focus on API correctness instead of presentation. |

---

## 🔹 Version 4 – Added Constraints

### Prompt

```text
Must return 400 with JSON error for missing/empty title, 404 with JSON error for unknown id, 201 on create, 204 on delete. Use Spring Validation (@Valid, @NotBlank) and a @ExceptionHandler for errors. Config must support switching from H2 to Postgres via application.properties without code changes.
```

### Commit

```text
v4: added constraints (status codes, validation, DB portability)
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Added explicit validation, status code, and portability requirements. |
| **Improved** | Added Bean Validation, JSON error responses, proper HTTP status codes, exception handling, and H2 ↔ PostgreSQL portability. |
| **Still Failed** | No automated verification that every requirement was met. |
| **Try Next** | Add a self-review step after generation. |

---

## 🔹 Version 5 – Added Review Instructions

### Prompt

```text
After writing the code, review it against these criteria: correct status codes for every case, H2→Postgres portability (no hardcoded DB-specific SQL), and whether Swagger/OpenAPI annotations are present. List any gaps before finalizing.
```

### Commit

```text
v5: added review instructions (self-check against criteria)
```

### Notes

| Category | Description |
|----------|-------------|
| **Changed** | Added a verification step after code generation. |
| **Improved** | Verified status codes, database portability, and Swagger/OpenAPI support before finalizing. |
| **Still Failed** | Review quality depends on the supplied checklist. |
| **Try Next** | Ask clarifying questions before generating code when requirements are ambiguous. |

---

# ✅ Current Features

- RESTful CRUD API
- Java 25
- Spring Boot
- Spring Data JPA
- H2 Database
- Bean Validation
- Global Exception Handling
- Proper HTTP Status Codes
- JSON Error Responses
- H2 ↔ PostgreSQL Portability
- Layered Architecture
- Prompt Engineering Workflow

---

# 📁 Project Structure

```text
src
├── controller
├── service
├── repository
├── model
├── exception
└── resources
```

---

# ▶️ Running the Project

### Clone the Repository

```bash
git clone https://github.com/your-username/prompt-ladder.git
```

### Navigate to the Project

```bash
cd prompt-ladder
```

### Run the Application

```bash
mvn spring-boot:run
```

### Application URL

```text
http://localhost:8080
```

### H2 Console

```text
http://localhost:8080/h2-console
```

---

# 🎯 Final Reusable Prompt

```text
You are a senior Java backend engineer.

Build a production-ready Task Manager REST API using Java 25, Spring Boot, Spring Data JPA, Maven, and PostgreSQL-compatible JPA.

Requirements:
- Use layered architecture (Controller, Service, Repository, Entity).
- Implement full CRUD operations.
- Use Bean Validation (@Valid, @NotBlank).
- Return proper HTTP status codes:
  - 200 OK
  - 201 Created
  - 204 No Content
  - 400 Bad Request
  - 404 Not Found
- Return JSON error responses using @RestControllerAdvice.
- Use Spring Data JPA without database-specific SQL.
- Configure the application so it can switch between H2 and PostgreSQL using only application.properties.
- Add Swagger/OpenAPI annotations for all endpoints.
- Organize the response file-by-file.
- Respond with code only.
- After generating the code, review it against these criteria:
  1. Correct HTTP status codes
  2. Validation works correctly
  3. H2 ↔ PostgreSQL portability
  4. Swagger/OpenAPI coverage
  5. List any remaining gaps before finalizing.
```

---

# 📚 Learning Outcome

This project demonstrates that **better prompts produce better software**.

By progressively refining prompts—from a vague request to explicit technical constraints and review instructions—the generated backend becomes significantly more reliable, maintainable, and production-ready.

> **Key Takeaway:** Prompt engineering is an iterative process. Small, well-targeted improvements to prompts can dramatically improve the quality, correctness, and maintainability of AI-generated code.

