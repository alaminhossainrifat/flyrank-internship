# AI Decision Flow — Frontend

A visual AI workflow editor built with React Flow.

The application allows users to create, edit, and connect AI decision nodes. Each node contains a prompt and provides `YES` and `NO` branches. When the workflow is executed, the frontend communicates with the Spring Boot backend and dynamically follows the path selected by the AI.

---

## 🚀 Overview

The AI Decision Flow frontend provides a visual interface for building and executing AI-powered decision workflows.

Users can:

- Create and edit decision prompts
- Visualize workflow nodes
- Connect nodes
- Define YES/NO paths
- Execute the workflow
- Communicate with the backend AI engine
- View execution logs
- See active nodes during execution
- See animated edges based on AI decisions

---

## ✨ Key Features

### 🎨 Visual Workflow Editor

The application uses React Flow to provide an interactive canvas for building decision workflows.

Users can:

- Move nodes
- Connect nodes
- Edit prompts
- Visualize branching paths
- Control the workflow visually

---

### 🧩 Custom Prompt Nodes

Each decision node contains:

- Node title
- Editable prompt
- YES output
- NO output

Example:

    ┌─────────────────────────────────┐
    │ Check Request Type              │
    │                                 │
    │ Is this a support request?      │
    │                                 │
    │ YES ●                     ● NO  │
    └─────────────────────────────────┘

---

### 🔀 YES / NO Branching

Each node supports two possible paths:

                     Decision Node
                           │
                   ┌───────┴───────┐
                   │               │
                  YES             NO
                   │               │
                   ▼               ▼
              Next Node A      Next Node B

The selected path depends on the AI response received from the backend.

---

### ▶️ Run Workflow

The `Run Workflow` action starts execution from the first node.

Execution flow:

    Start Node
        ↓
    Send Prompt
        ↓
    Spring Boot Backend
        ↓
    OpenRouter LLM
        ↓
    YES / NO
        ↓
    Select Edge
        ↓
    Next Node
        ↓
    Repeat

---

### 📊 Execution Logs

The application includes an execution log panel that displays workflow activity.

Example:

    Workflow execution started...
    Executing Node: Check Request Type
    AI Response: YES
    Routing to next node via YES path...
    Executing Node: Support Logic

The logs help users understand how the workflow is being executed.

---

### 🎯 Visual Execution State

During execution:

- The currently executing node is highlighted.
- Execution status is visually displayed.
- The selected edge is animated.
- YES and NO paths are visually distinguishable.
- Execution logs are displayed.

This makes the workflow easier to understand and debug.

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| React | Frontend framework |
| TypeScript | Type-safe development |
| Vite | Development/build tool |
| React Flow | Visual workflow editor |
| Axios | Backend API communication |
| Tailwind CSS | UI styling |

---

## 📁 Project Structure

    ai-decision-frontend/
    ├── public/
    │
    ├── src/
    │   ├── components/
    │   │   ├── WorkflowEditor.tsx
    │   │   │
    │   │   └── nodes/
    │   │       └── PromptNode.tsx
    │   │
    │   ├── App.tsx
    │   ├── main.tsx
    │   └── index.css
    │
    ├── index.html
    ├── package.json
    ├── package-lock.json
    ├── tailwind.config.js
    ├── postcss.config.js
    ├── tsconfig.json
    ├── vite.config.ts
    └── README.md

> The exact structure may vary depending on the current project configuration.

---

## ⚙️ Requirements

Make sure the following are installed:

- Node.js
- npm
- Git
- Modern web browser
- Running Spring Boot backend

Verify Node.js:

    node --version

Verify npm:

    npm --version

---

## 📦 Installation

Clone the repository:

    git clone <YOUR-FRONTEND-REPOSITORY-URL>

Move into the project directory:

    cd ai-decision-frontend

Install dependencies:

    npm install

---

## 📚 Main Dependencies

React Flow and Axios:

    npm install reactflow axios

Tailwind CSS:

    npm install -D tailwindcss postcss autoprefixer

---

## ▶️ Running the Application

Start the Vite development server:

    npm run dev

The application will normally be available at:

    http://localhost:5173

---

## 🔗 Backend Connection

The frontend communicates with the Spring Boot backend.

Backend:

    http://localhost:8080

Decision API:

    POST http://localhost:8080/api/decision/evaluate

The frontend sends:

    {
      "prompt": "Is this a technical support request?"
    }

The backend returns:

    {
      "decision": "YES"
    }

or:

    {
      "decision": "NO"
    }

---

## 🔄 Workflow Execution

The frontend maintains the workflow as a graph consisting of:

    Nodes + Edges

### Nodes

Each node contains information such as:

    id
    type
    label
    prompt
    position

### Edges

Each edge connects two nodes and represents a possible decision path.

Example:

    Node 1
     ├── YES ──→ Node 2
     └── NO  ──→ Node 3

---

## 🧠 Example Workflow

A simple workflow can look like:

                 ┌──────────────────────────┐
                 │ Check Request Type       │
                 │                          │
                 │ Is this a technical      │
                 │ support request?         │
                 └────────────┬─────────────┘
                              │
                     ┌────────┴────────┐
                     │                 │
                    YES               NO
                     │                 │
                     ▼                 ▼
          ┌──────────────────┐   ┌──────────────────┐
          │ Support Logic    │   │ Sales Logic      │
          │                  │   │                  │
          │ Is the device    │   │ Does customer    │
          │ turning on?      │   │ want a laptop?   │
          └──────────────────┘   └──────────────────┘

---

## ▶️ Running a Workflow

### Step 1 — Start Backend

Make sure the Spring Boot backend is running:

    http://localhost:8080

### Step 2 — Start Frontend

Run:

    npm run dev

Open:

    http://localhost:5173

### Step 3 — Edit a Prompt

Example:

    Is this a technical support request?

### Step 4 — Run Workflow

Click:

    Run Workflow

### Step 5 — Observe Execution

The application will:

    Highlight Current Node
            ↓
       Send AI Request
            ↓
        Receive YES/NO
            ↓
       Select Active Edge
            ↓
        Move to Next Node
            ↓
       Update Execution Logs

---

## 📊 Execution Logs

The execution log panel displays runtime activity.

Example:

    Workflow execution started...

    Executing Node:
    [Check Request Type]

    AI Response:
    YES

    Routing to next node via YES path...

    Executing Node:
    [Support Logic]

    AI Response:
    NO

The log panel helps users understand exactly which nodes were executed and which decisions were returned.

---

## 🎨 Visual Execution

### Active Node

The currently executing node is visually highlighted.

### Active Edge

The edge selected by the AI becomes animated.

### YES Path

When the AI returns:

    YES

The corresponding YES path is highlighted.

### NO Path

When the AI returns:

    NO

The corresponding NO path is highlighted.

---

## 🧩 Prompt Editing

Prompts can be edited directly inside decision nodes.

Example:

    Original:
    Is this a technical support request?

Can be changed to:

    Is the customer asking for technical assistance?

The updated prompt is stored in the workflow state and used during execution.

---

## 🔌 API Integration

Axios is used to communicate with the Spring Boot backend.

Example:

    const response = await axios.post(
      'http://localhost:8080/api/decision/evaluate',
      {
        prompt: currentNode.data.prompt
      }
    );

    const decision = response.data.decision;

The response determines which edge should be followed.

---

## 🌐 Development Architecture

    ┌─────────────────────────────────────────────┐
    │              React Frontend                 │
    │                                             │
    │  ┌───────────────────────────────────────┐  │
    │  │          React Flow Canvas            │  │
    │  │                                       │  │
    │  │   Node ── YES ──→ Node                │  │
    │  │    │                                  │  │
    │  │    └──── NO ───→ Node                 │  │
    │  └───────────────────────────────────────┘  │
    │                    │                        │
    │                    │ Axios                  │
    └────────────────────┼────────────────────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   Spring Boot API     │
              │      :8080            │
              └──────────┬───────────┘
                         │
                         ▼
                  ┌──────────────┐
                  │  OpenRouter  │
                  │     LLM      │
                  └──────────────┘

---

## 🐛 Troubleshooting

### `npm run dev` Cannot Find `package.json`

Make sure you are inside the frontend project directory:

    cd ai-decision-frontend

Then:

    npm install
    npm run dev

---

### Backend Connection Error

Make sure Spring Boot is running:

    http://localhost:8080

Then test:

    POST http://localhost:8080/api/decision/evaluate

---

### CORS Error

Make sure the backend allows requests from:

    http://localhost:5173

Restart Spring Boot after changing CORS configuration.

---

### React Flow Styling Problems

Make sure React Flow's stylesheet is imported:

    import 'reactflow/dist/style.css';

---

## 🔒 Security Notes

The frontend must never contain the OpenRouter API key.

The API key belongs in the backend.

Correct architecture:

    React
      ↓
    Spring Boot
      ↓
    OpenRouter

Incorrect architecture:

    React
      ↓
    OpenRouter + Secret API Key

Never expose the OpenRouter API key in:

- React source code
- Public `.env` files
- GitHub repositories
- Browser local storage
- Public frontend builds

---

## 📌 Assignment Context

This frontend is part of the Week 7 — AI Decision Flow assignment.

The objective is to build a visual AI workflow where:

1. Users create a graph.
2. Each node contains an AI decision prompt.
3. The AI returns `YES` or `NO`.
4. The workflow follows the corresponding edge.
5. The execution path is visualized.

The assignment focuses on React Flow for the visual workflow editor and AI-powered YES/NO branching.

---

## 🚧 Future Improvements

Potential improvements include:

- Add nodes dynamically
- Delete nodes and edges
- Save/load workflows
- JSON export/import
- Persistent workflow storage
- Execution history
- Retry failed nodes
- Better error handling
- Workflow validation
- Production API configuration
- Authentication
- Responsive mobile UI
- WebSocket-based live execution updates
- Inngest-based workflow execution

---

## 👨‍💻 Author

**Al Amin Hossain Rifat**

Backend AI Engineering — Week 7 Assignment

---

## 📄 License

This project was developed for educational and internship assignment purposes.