# AI Decision Flow — Backend

A Spring Boot backend for executing AI-powered decision workflows.

The backend receives decision prompts, sends them to an LLM through OpenRouter, processes the response, and returns a normalized `YES` or `NO` decision. The React frontend uses this API to dynamically traverse the visual decision flow.

---

## 🚀 Overview

The AI Decision Flow backend is responsible for:

- Receiving decision prompts from the frontend
- Sending prompts to an LLM through OpenRouter
- Processing AI responses
- Normalizing the result into `YES` or `NO`
- Exposing REST APIs for frontend integration
- Supporting CORS for the React frontend
- Providing a clean controller-service architecture

### High-Level Flow

    React Frontend
          │
          │ POST /api/decision/evaluate
          ▼
    Spring Boot REST API
          │
          ▼
    Decision Controller
          │
          ▼
    AI Decision Service
          │
          ▼
    OpenRouter API
          │
          ▼
    Free LLM Model
          │
          ▼
        YES / NO
          │
          ▼
    Spring Boot Response
          │
          ▼
    React Flow Engine

---

## ✨ Features

### AI Decision Evaluation

The backend evaluates natural-language prompts and returns a binary decision.

Example response:

    {
      "decision": "YES"
    }

or:

    {
      "decision": "NO"
    }

### REST API

The backend provides a REST API that can be consumed by:

- React
- Postman
- Other frontend clients
- External API clients

### OpenRouter Integration

The project uses OpenRouter's OpenAI-compatible API to communicate with an LLM.

The free model used during development is:

    google/gemma-4-26b-a4b-it:free

OpenRouter API endpoint:

    https://openrouter.ai/api/v1/chat/completions

### CORS Support

The backend supports communication with the React development server.

Typical development setup:

    Frontend: http://localhost:5173
    Backend:  http://localhost:8080

### Separation of Concerns

The backend follows a layered architecture:

    Controller
        ↓
    Service
        ↓
    AI Provider

This keeps the REST API layer separate from AI processing logic.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring AI | AI/LLM integration |
| OpenRouter | LLM API provider |
| Maven | Dependency management |
| Lombok | Boilerplate reduction |
| Postman | API testing |

---

## 📁 Project Structure

    ai_decision_flow/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/
    │   │   │       └── yourpackage/
    │   │   │           ├── controller/
    │   │   │           │   ├── DecisionController.java
    │   │   │           │   └── HealthController.java
    │   │   │           │
    │   │   │           ├── service/
    │   │   │           │   └── AiDecisionService.java
    │   │   │           │
    │   │   │           ├── dto/
    │   │   │           │   ├── AiDecisionRequest.java
    │   │   │           │   └── AiDecisionResponse.java
    │   │   │           │
    │   │   │           └── AiDecisionFlowApplication.java
    │   │   │
    │   │   └── resources/
    │   │       └── application.properties
    │   │
    │   └── test/
    │
    ├── pom.xml
    └── README.md

> Package names may vary depending on the actual project configuration.

---

## ⚙️ Requirements

Before running the backend, make sure the following are installed:

- Java 25 or compatible configured Java version
- Maven
- Git
- IntelliJ IDEA / VS Code / Eclipse
- OpenRouter API key
- Postman (recommended)

Verify Java:

    java --version

Verify Maven:

    mvn --version

---

## 🔐 Environment Configuration

Never commit API keys directly to GitHub.

Configure the OpenRouter API key using an environment variable.

Example `application.properties`:

    server.port=8080

    spring.ai.openai.api-key=${OPENROUTER_API_KEY}

    spring.ai.openai.base-url=https://openrouter.ai/api

    spring.ai.openai.chat.options.model=google/gemma-4-26b-a4b-it:free

### Windows PowerShell

    $env:OPENROUTER_API_KEY="your-api-key"

### Windows CMD

    set OPENROUTER_API_KEY=your-api-key

### Linux / macOS

    export OPENROUTER_API_KEY="your-api-key"

> Never push your real API key to GitHub.

---

## 📦 Installation

Clone the repository:

    git clone <YOUR-BACKEND-REPOSITORY-URL>

Move into the project:

    cd ai_decision_flow

Build the project:

    mvn clean install

---

## ▶️ Running the Application

Start the Spring Boot application:

    mvn spring-boot:run

Or run the main application class directly from IntelliJ IDEA.

The backend will start on:

    http://localhost:8080

---

## 🔌 API Reference

### Evaluate Decision

Evaluates a prompt and returns `YES` or `NO`.

#### Endpoint

    POST /api/decision/evaluate

#### Full URL

    http://localhost:8080/api/decision/evaluate

#### Headers

    Content-Type: application/json

#### Request Body

    {
      "prompt": "Is this a technical support request?"
    }

#### Successful Response

    {
      "decision": "YES"
    }

Another example:

    {
      "prompt": "I want to buy a new laptop."
    }

Possible response:

    {
      "decision": "NO"
    }

---

## 🧪 Testing with Postman

### Step 1 — Start the Backend

Make sure the Spring Boot application is running.

### Step 2 — Create a Postman Request

Method:

    POST

URL:

    http://localhost:8080/api/decision/evaluate

### Step 3 — Configure Request Body

Select:

    Body → raw → JSON

Use:

    {
      "prompt": "Is 'My computer won't turn on' a technical support request?"
    }

### Step 4 — Send Request

Expected response format:

    {
      "decision": "YES"
    }

Try another prompt:

    {
      "prompt": "I want to buy a new laptop."
    }

Expected response format:

    {
      "decision": "NO"
    }

---

## 🩺 Health Check

The project includes a simple system health endpoint for verifying that the backend is running.

Typical endpoint:

    GET /api/system/health

Full URL:

    http://localhost:8080/api/system/health

---

## 🔄 Decision Flow Integration

The backend works with the React Flow frontend.

Example workflow:

                     ┌─────────────────────────┐
                     │        Start Node       │
                     │ Is this a support       │
                     │ request?                │
                     └────────────┬────────────┘
                                  │
                       ┌──────────┴──────────┐
                       │                     │
                      YES                   NO
                       │                     │
                       ▼                     ▼
              ┌─────────────────┐   ┌─────────────────┐
              │ Support Node    │   │ Sales Node      │
              │ Is the device   │   │ Customer wants  │
              │ turning on?     │   │ a laptop?       │
              └─────────────────┘   └─────────────────┘

The frontend determines which edge to follow based on the backend's AI decision.

---

## 🧠 AI Decision Logic

The workflow follows a binary decision model:

    Prompt
      ↓
    LLM
      ↓
    YES / NO
      ↓
    Select matching edge
      ↓
    Move to next node

Each decision node acts as an AI-powered branching point.

---

## 🌐 CORS

The backend supports communication with the React development server.

Development environment:

    React:
    http://localhost:5173

    Spring Boot:
    http://localhost:8080

For production deployment, CORS should be restricted to trusted domains.

---

## 🐛 Troubleshooting

### 404 from OpenRouter

Verify the OpenRouter base URL:

    spring.ai.openai.base-url=https://openrouter.ai/api

Verify the configured model:

    spring.ai.openai.chat.options.model=google/gemma-4-26b-a4b-it:free

Make sure the selected model is available through OpenRouter.

### API Key Error

Verify that the following environment variable is correctly configured:

    OPENROUTER_API_KEY

Restart the application after changing environment variables.

### Backend Does Not Start

Check Java:

    java --version

Check Maven:

    mvn --version

Then rebuild:

    mvn clean install

### CORS Error

Make sure the backend is running on:

    http://localhost:8080

And the frontend is running on:

    http://localhost:5173

---

## 🔒 Security Notes

- Never commit API keys.
- Use environment variables for secrets.
- Do not expose API credentials in frontend code.
- Restrict CORS in production.
- Use HTTPS in production.
- Rotate API keys if they are accidentally exposed.

---

## 📌 Assignment Context

This backend is part of the Week 7 — AI Decision Flow assignment.

The goal is to build a visual AI workflow where each node represents a decision step and the AI returns either:

    YES

or:

    NO

The selected decision determines which connected node should execute next.

---

## 🚧 Future Improvements

Possible future improvements include:

- Inngest-based workflow execution
- Persistent workflow storage
- Execution history database
- Retry mechanism
- Structured execution logs
- Authentication and authorization
- Production-grade CORS configuration
- API rate limiting
- Centralized exception handling
- Docker deployment
- Automated tests

---

## 👨‍💻 Author

**Alamin Hossain Rifat**

Backend AI Engineering — Week 7 Assignment

---

## 📄 License

This project was developed for educational and internship assignment purposes.