# 🚀 Widget Platform Backend

> A multi-tenant embeddable widget and lead-capture platform built with Spring Boot and PostgreSQL.

![Java](https://img.shields.io/badge/Java-25-orange)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

---

## 📌 Project Overview

Widget Platform Backend is a RESTful API that allows businesses to create embeddable widgets, collect customer submissions, and manage leads securely.

The platform is designed with **multi-tenant architecture**, allowing each owner to access only their own widgets and submissions.

---

# ✨ Features

- ✅ Create embeddable widgets
- ✅ Multi-tenant architecture
- ✅ Lead submission API
- ✅ PostgreSQL database
- ✅ Hibernate ORM
- ✅ Spring Data JPA
- ✅ Layered Architecture
- ✅ REST API
- ✅ JSONB Support
- ✅ UUID Primary Keys
- ✅ Geo-Enrichment (IP → Location)
- ✅ Safe Side Effects (Resilient Email Notifications)
- ✅ CORS Protection
- ✅ Rate Limiting
- ✅ Honeypot Spam Protection
- ✅ Future-ready for Authentication & JWT

---

# 🛠 Tech Stack

| Technology | Version |
|------------|----------|
| Java | 25 |
| Spring Boot | 4.1 |
| Spring Data JPA | Latest |
| Hibernate | 7.x |
| PostgreSQL | 17 |
| Maven | Latest |
| Lombok | Latest |

---

# 📁 Project Structure

```text
widget-platform-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── rifat/
│   │   │           └── widget_platform_backend/
│   │   │               ├── config/
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller/
│   │   │               │   ├── DeliveryController.java
│   │   │               │   ├── SubmissionController.java
│   │   │               │   └── WidgetController.java
│   │   │               ├── entity/
│   │   │               │   ├── Submission.java
│   │   │               │   └── Widget.java
│   │   │               ├── filter/
│   │   │               │   └── RateLimitFilter.java
│   │   │               ├── repository/
│   │   │               │   ├── SubmissionRepository.java
│   │   │               │   └── WidgetRepository.java
│   │   │               ├── service/
│   │   │               │   ├── GeoEnrichmentService.java
│   │   │               │   ├── NotificationService.java
│   │   │               │   └── WidgetService.java
│   │   │               └── WidgetPlatformBackendApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.yml
│   └── test/
│       └── java/
│           └── com/
│               └── rifat/
│                   └── widget_platform_backend/
│                       └── WidgetPlatformBackendApplicationTests.java
├── target/
│   ├── classes/
│   │   ├── com/
│   │   │   └── rifat/
│   │   │       └── widget_platform_backend/
│   │   │           ├── config/
│   │   │           │   └── SecurityConfig.class
│   │   │           ├── controller/
│   │   │           │   ├── DeliveryController.class
│   │   │           │   ├── SubmissionController.class
│   │   │           │   └── WidgetController.class
│   │   │           ├── entity/
│   │   │           │   ├── Submission.class
│   │   │           │   └── Widget.class
│   │   │           ├── filter/
│   │   │           │   └── RateLimitFilter.class
│   │   │           ├── repository/
│   │   │           │   ├── SubmissionRepository.class
│   │   │           │   └── WidgetRepository.class
│   │   │           ├── service/
│   │   │           │   ├── GeoEnrichmentService.class
│   │   │           │   ├── NotificationService.class
│   │   │           │   └── WidgetService.class
│   │   │           └── WidgetPlatformBackendApplication.class
│   │   └── application.yml
│   └── generated-sources/
│       └── annotations/
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

# ⚙ Configuration

Example configuration:

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

---

# ▶ Running the Project

## Clone

```bash
git clone https://github.com/alaminhossainrifat/flyrank-internship/tree/main/backend-ai-engineering/week08/widget-platform-backend
```

## Go to project

```bash
cd widget-platform-backend
```

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

Application starts at

```
http://localhost:8080
```

---

# 📡 REST API

## Create Widget

**POST**

```
/api/widgets
```

Example Body

```json
{
  "name": "My First Signup Form",
  "type": "signup",
  "allowedOrigins": "http://localhost:3000"
}
```

---

## Get Widgets

**GET**

```
/api/widgets
```

---

## Widget Configuration

**GET**

```
/api/widgets/{id}/config
```

---

## Submit Lead

**POST**

```
/api/submissions?widgetId={widgetId}
```

Example

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "017xxxxxxxx"
}
```

### Security Notes

- 🛡 Requests are protected by **Rate Limiting**.
- 🚫 Spam submissions are filtered using a **Honeypot** field.
- 🌍 Client IP is enriched with approximate geographic information.
- 📧 Email notifications are executed as safe side effects so failures never block successful submissions.

> **Honeypot**
>
> If the request contains a hidden field named `_bot_check`, the submission will be silently discarded as spam.

---

# 🧩 Architecture

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
PostgreSQL
```

---

# 🔒 Security

## Current Status

- ✅ Basic Spring Security
- ✅ CORS Configuration
- ✅ Rate Limiting
- ✅ Honeypot Spam Protection
- ✅ Safe Side Effects for Email Notifications
- ✅ Development Configuration

## Upcoming

- JWT Authentication
- Role-Based Authorization
- Request Validation Improvements
- Audit Logging

---

# 🚧 Roadmap

- [x] Project Setup
- [x] PostgreSQL Integration
- [x] Entity Design
- [x] Repository Layer
- [x] Service Layer
- [x] REST Controllers
- [x] CORS Configuration
- [x] Rate Limiting
- [x] Honeypot Spam Protection
- [ ] JWT Authentication
- [ ] Validation
- [ ] Analytics Dashboard
- [ ] Docker
- [ ] CI/CD
- [ ] Deployment

---

# 📜 License

This project is licensed under the MIT License.

---

# 👨‍💻 Author

**Md. Al Amin Hossain Rifat**

Full-stack Developer

Java • Spring Boot • PostgreSQL

GitHub: https://github.com/alaminhossainrifat