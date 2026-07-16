package com.flyrank.crudapi.controller;

import com.flyrank.crudapi.dto.TaskRequest;
import com.flyrank.crudapi.model.Task;
import com.flyrank.crudapi.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Read endpoints for /tasks.
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    // GET /tasks - list all tasks.
    @GetMapping
    public List<Task> getAll() {
        return service.findAll();
    }

    // GET /tasks/{id} - single task, 404 if missing.
    @GetMapping("/{id}")
    public Task getOne(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /tasks - create task, 201, 400 if title invalid.
    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody TaskRequest request) {
        Task created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
