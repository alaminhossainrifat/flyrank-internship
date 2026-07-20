# Task CRUD API — FlyRank Backend AI Engineering

RESTful CRUD API built with **Spring Boot**, backed by **PostgreSQL**, fully containerized with **Docker Compose**.

Built as part of the **FlyRank AI Internship — Backend AI Engineering track**.
- **Week 2 (A2 — Build your first CRUD API):** in-memory Task CRUD API.
- **Week 3 (BE-04 — Containerize your stack):** swapped in-memory store for Postgres, containerized full stack.

## 🚀 Tech Stack

| Layer | Tech |
|---|---|
| Language / Framework | Java, Spring Boot |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Build | Maven |
| Containerization | Docker, Docker Compose |
| API Testing | Postman |
| Docs | Swagger / OpenAPI |

## 🎯 Week 3 Assignment — What Changed

- **Postgres in Docker with a volume (`pgdata`)** — data survives container restarts.
- **`.env` for secrets** — connection string, DB user/password live in `.env` (gitignored). `.env.example` committed for reference.
- **DB table via SQL init script** — schema created for the `Task` entity.
- **New `PostgresTaskRepository`** implements the existing `TaskRepository` interface.
- **`TaskService` and `TaskController` — zero changes.** Only the repository implementation was swapped. This proves the layered architecture: storage is an implementation detail, not something the business logic or routes need to know about.
- **`docker-compose.yml`** — app + Postgres start together with one command: `docker compose up`.
- **Persistence proven** — see below.

## 🛠️ How to Run

### Prerequisites
- Docker + Docker Compose installed.

### 1. Setup environment
```bash
cp .env.example .env
```

### 2. Start the full stack (app + db)
```bash
docker compose up --build -d
```
First run takes a few minutes (Maven build).

### 3. Stop the stack (keeps data)
```bash
docker compose down
```
To wipe the database volume too (fresh start):
```bash
docker compose down -v
```

## ✅ Persistence Proof (how I checked)

1. Added tasks via Postman `POST /tasks`.
2. Ran `docker compose down` (stops app + db containers).
3. Ran `docker compose up -d` again.
4. Called `GET /tasks` — same rows were still there.

Data survives because Postgres writes to the named volume `pgdata`, which isn't removed by a normal `down`/`up` cycle (only `down -v` removes it).

## 📡 API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/health` | API health check |
| GET | `/tasks` | List all tasks |
| GET | `/tasks/{id}` | Get one task by id |
| POST | `/tasks` | Create a task — body: `{"title": "Task Name", "done": false}` |
| PUT | `/tasks/{id}` | Update a task's title/status |
| DELETE | `/tasks/{id}` | Delete a task |

## 📚 API Docs (Swagger)

Once running:
- Swagger UI: `http://localhost:8080/docs`

## 🗂️ Architecture Note

```
TaskController → TaskService → TaskRepository (interface)
                                      ├── InMemoryTaskRepository   (Week 2)
                                      └── PostgresTaskRepository   (Week 3, active)
```
Only the repository implementation changed between weeks. Controller and Service are untouched — this is the whole point of the layered design.