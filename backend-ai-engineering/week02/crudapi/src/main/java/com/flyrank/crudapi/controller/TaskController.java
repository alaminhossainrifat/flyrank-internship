package com.flyrank.crudapi.controller;

import com.flyrank.crudapi.model.Task;
import com.flyrank.crudapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
