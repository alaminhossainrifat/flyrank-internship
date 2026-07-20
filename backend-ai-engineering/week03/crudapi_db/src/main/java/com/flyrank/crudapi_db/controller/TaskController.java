package com.flyrank.crudapi_db.controller;

import com.flyrank.crudapi_db.dto.TaskRequest;
import com.flyrank.crudapi_db.model.Task;
import com.flyrank.crudapi_db.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST API endpoints for managing tasks.
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    // GET /tasks - Retrieves the list of all tasks.
    @GetMapping
    public List<Task> getAll() {
        return service.findAll();
    }

    // GET /tasks/{id} - Retrieves a single task, returns 404 if missing.
    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /tasks - Creates a new task, returns 201 on success, 400 if invalid.
    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskRequest request) {
        Task created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // PUT /tasks/{id} - Updates an existing task, returns 404 if missing.
    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return service.update(id, request);
    }

    // DELETE /tasks/{id} - Removes a task, returns 204 on success, 404 if missing.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}