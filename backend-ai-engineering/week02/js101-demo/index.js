// Import Express framework
const express = require('express');

// Create Express application instance (similar to Spring Boot's application context)
const app = express();
const PORT = 3000;

// Middleware: parse incoming JSON request bodies (similar to @RequestBody auto-binding in Spring)
app.use(express.json());

// Middleware: simple request logger (similar to a Spring Interceptor/Filter)
app.use((req, res, next) => {
    console.log(`${req.method} ${req.url}`);
    next(); // pass control to next middleware/route handler
});

// In-memory data store (acts like a fake database for demo purposes)
let students = [
    { id: 1, name: 'Rifat' },
    { id: 2, name: 'Nusrat' }
];

// GET endpoint: fetch all students (similar to @GetMapping("/api/students"))
app.get('/api/students', (req, res) => {
    res.json(students);
});

// GET endpoint: fetch single student by id (similar to @GetMapping("/api/students/{id}"))
app.get('/api/students/:id', (req, res) => {
    const student = students.find(s => s.id === parseInt(req.params.id));
    if (!student) {
        return res.status(404).json({ message: 'Student not found' });
    }
    res.json(student);
});

// POST endpoint: add new student (similar to @PostMapping with @RequestBody)
app.post('/api/students', (req, res) => {
    const newStudent = {
        id: students.length + 1,
        name: req.body.name
    };
    students.push(newStudent);
    res.status(201).json(newStudent);
});

// Start server (similar to Spring Boot's embedded Tomcat startup)
app.listen(PORT, () => {
    console.log(`Server running at http://localhost:${PORT}`);
});