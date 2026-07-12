# Build Your First API Endpoint

**Track:** Backend AI Engineering | **Week:** 1 | **Stack:** Java 17 + Spring Boot 3

## Goal
Build the smallest possible backend with two JSON endpoints, tested via curl/Postman, and published to GitHub.

## Endpoints
| Method | Path | Response |
|--------|------|----------|
| GET | /api/hello | {"message": "Hello from FlyRank BE-01!"} |
| GET | /api/status | {"status": "UP", "timestamp": "..."} |

## How to Run
```bash
mvn spring-boot:run
```
Server starts on `http://localhost:8080`.

## How to Test
Tested manually using **Postman**:
- GET `http://localhost:8080/api/hello` → 200 OK
- GET `http://localhost:8080/api/status` → 200 OK

## Run Automated Tests
```bash
mvn test
```

## Status
✅ Completed and verified working.