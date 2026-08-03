# 🚀 Widget Platform Backend

<div align="center">

### Multi-Tenant Embeddable Widget & Lead-Capture Platform

A scalable backend application built with **Java 25**, **Spring Boot 4.1**, and **PostgreSQL** for creating embeddable widgets, collecting customer submissions, and providing secure dashboard analytics.

![Java](https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791?style=for-the-badge&logo=postgresql)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build-red?style=for-the-badge&logo=apachemaven)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

</div>

---

# 📖 Project Overview

Widget Platform Backend is a production-oriented REST API designed for businesses that need to embed customizable widgets into their websites and securely collect customer information.

The application follows a **multi-tenant architecture**, ensuring complete isolation between organizations while providing a scalable backend for widget delivery, lead capture, analytics, spam protection, and future authentication.

---

# 📑 Table of Contents

- Features
- Tech Stack
- Project Structure
- Database Schema
- Architecture
- Configuration
- Getting Started
- REST API
- Security
- Development Progress
- Roadmap
- Future Improvements
- License
- Author

---

# ✨ Features

- ✅ Embeddable Widget Management
- ✅ Multi-Tenant Architecture
- ✅ Widget Delivery API
- ✅ Lead Submission API
- ✅ Dashboard Analytics API
- ✅ PostgreSQL Integration
- ✅ Hibernate ORM
- ✅ Spring Data JPA
- ✅ Layered Architecture
- ✅ RESTful APIs
- ✅ UUID Primary Keys
- ✅ JSONB Payload Storage
- ✅ JSONB Geo Location Storage
- ✅ Geo-Enrichment (IP → Location)
- ✅ Safe Side Effects (Resilient Email Notifications)
- ✅ Global Exception Handling
- ✅ Request Validation
- ✅ CORS Protection
- ✅ Rate Limiting
- ✅ Honeypot Spam Protection
- ✅ Dashboard-ready Data
- ✅ Future-ready JWT Authentication

---

# 🛠 Tech Stack

| Category | Technology |
|------------|------------|
| Language | Java 25 |
| Framework | Spring Boot 4.1 |
| ORM | Hibernate 7 |
| Database | PostgreSQL 17 |
| Build Tool | Maven |
| Persistence | Spring Data JPA |
| Boilerplate | Lombok |
| Security | Spring Security |

---

# 📁 Project Structure

```text
widget-platform-backend/
│
├── demo-website/
│   └── index.html
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │
│   │   └── com/rifat/widget_platform_backend/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── entity/
│   │       ├── exception/
│   │       ├── filter/
│   │       ├── repository/
│   │       ├── service/
│   │       └── WidgetPlatformBackendApplication.java
│   │
│   └── resources/
│       ├── static/
│       │   └── widget.js
│       ├── templates/
│       └── application.yml
│
├── BUILDLOG.md
├── capstone.yaml
├── EVIDENCE.md
├── HELP.md
├── README.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

---

# 🗄 Database Schema

## Widget

| Field | Type |
|------|------|
| id | UUID |
| name | String |
| type | String |
| ownerId | String |
| allowedOrigins | String |
| createdAt | Timestamp |

---

## Submission

| Field | Type |
|------|------|
| id | UUID |
| widget | Widget |
| payload | JSONB |
| geoLocation | JSONB |
| ipAddress | String |
| submittedAt | Timestamp |

---

# 🏗 Architecture

```text
                 Client Website
                        │
                        ▼
               Spring Security
                        │
                        ▼
              Rate Limiting Filter
                        │
                        ▼
                 REST Controllers
                        │
                        ▼
                  Service Layer
        ┌───────────────┼──────────────┐
        ▼               ▼              ▼
 Widget Service   GeoEnrichment   Notification
                        │
                        ▼
               Repository Layer
                        │
                        ▼
                  PostgreSQL 17
```

---

# ⚙ Configuration

Example configuration

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/widget_platform
    username: postgres
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

> **Important**
>
> Never commit your real database password or API keys to GitHub.

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone https://github.com/alaminhossainrifat/flyrank-internship.git
```

---

## Navigate

```bash
cd flyrank-internship/backend-ai-engineering/week08/widget-platform-backend
```

---

## Build

```bash
mvn clean install
```

---

## Run

```bash
mvn spring-boot:run
```

Application starts at

```
http://localhost:8080
```

---

# 🌐 Demo Website

A simple frontend demo is included.

```
test-client/index.html
```

This page demonstrates how a client website can embed widgets and submit customer data to the backend.

---

# 📡 REST API

| Method | Endpoint | Description |
|----------|------------------------------|--------------------------------|
| POST | `/api/widgets` | Create Widget |
| GET | `/api/widgets` | Get Widgets |
| GET | `/api/widgets/{id}/config` | Widget Configuration |
| POST | `/api/submissions` | Submit Lead |
| GET | `/api/dashboard/widgets/{widgetId}/submissions` | Widget Submissions |
| GET | `/api/dashboard/widgets/{widgetId}/stats` | Dashboard Analytics |

---

## Create Widget

### POST

```
/api/widgets
```

Request

```json
{
  "name": "My First Signup Form",
  "type": "signup",
  "allowedOrigins": "http://localhost:3000"
}
```

Example Response

```json
{
  "id":"8e95b3...",
  "name":"My First Signup Form",
  "type":"signup",
  "ownerId":"user-1",
  "createdAt":"2026-08-02T14:10:30"
}
```

---

## Submit Lead

### POST

```
/api/submissions?widgetId={widgetId}
```

Request

```json
{
  "name":"John Doe",
  "email":"john@example.com",
  "phone":"017xxxxxxxx"
}
```

### Security Notes

- 🛡 Rate Limiting enabled
- 🚫 Honeypot Spam Protection
- 🌍 Automatic Geo-Enrichment
- 📧 Safe Email Notification
- ⚠ Payload Validation
- ✅ Standard JSON Error Response

### Honeypot

If a request contains a hidden field named

```
_bot_check
```

the submission will be silently discarded as spam.

---

## Dashboard Analytics

### GET

```
/api/dashboard/widgets/{widgetId}/submissions
```

Returns all successful submissions for a widget.

---

### GET

```
/api/dashboard/widgets/{widgetId}/stats
```

Returns

- Total submissions
- Country-wise statistics
- Basic analytics

---

# 🔒 Security

## Current Status

- ✅ Spring Security
- ✅ CORS Configuration
- ✅ Rate Limiting
- ✅ Honeypot Spam Protection
- ✅ Payload Validation
- ✅ Global Exception Handling
- ✅ Safe Email Notifications
- ✅ Geo-Enrichment
- ✅ Development Configuration

## Upcoming

- JWT Authentication
- Role-Based Authorization
- Audit Logging

---

# 📊 Development Progress

| Module | Status |
|-----------|------------|
| PostgreSQL Integration | ✅ |
| Entity Layer | ✅ |
| Repository Layer | ✅ |
| Service Layer | ✅ |
| REST Controllers | ✅ |
| Widget Delivery | ✅ |
| Dashboard Analytics | ✅ |
| Geo-Enrichment | ✅ |
| Notification Service | ✅ |
| Validation | ✅ |
| CORS | ✅ |
| Rate Limiting | ✅ |
| Honeypot | ✅ |
| JWT Authentication | ⏳ |
| Docker | ⏳ |
| CI/CD | ⏳ |

---

# 🚧 Roadmap

- [x] Spring Boot Setup
- [x] PostgreSQL Integration
- [x] Widget Entity
- [x] Submission Entity
- [x] Repository Layer
- [x] Service Layer
- [x] REST Controllers
- [x] Widget Delivery
- [x] Dashboard Analytics
- [x] CORS Configuration
- [x] Validation
- [x] Rate Limiting
- [x] Honeypot Spam Protection
- [x] Geo-Enrichment
- [x] Notification Service
- [ ] JWT Authentication
- [ ] Docker Support
- [ ] CI/CD
- [ ] Deployment

---

# 💡 Future Improvements

- OAuth2 Authentication
- Dashboard Charts
- Widget Themes
- Email Templates
- Docker Compose
- Kubernetes Deployment

---

# 📜 License

This project is licensed under the **MIT License**.

---

# 👨‍💻 Author

**Md. Al Amin Hossain Rifat**

Computer Science & Engineering (CSE) Student

Backend Developer • Java • Spring Boot • PostgreSQL

📍 Dhaka, Bangladesh


💼 LinkedIn

https://linkedin.com/in/alaminhossainrifat

---

<div align="center">

## ⭐ If you found this project useful, please consider giving it a Star!

Thank you for visiting this repository ❤️

</div>