package com.flyrank.prompt_ladder.controller;

import com.flyrank.prompt_ladder.model.Task;
import com.flyrank.prompt_ladder.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Task API", description = "Task Management REST API")
@RestController
@RequestMapping("/tasks")
public class TaskController {

    // Service dependency
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // GET /tasks
    @Operation(summary = "Get all tasks")
    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    // GET /tasks/{id}
    @Operation(summary = "Get task by id")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = service.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // POST /tasks
    @Operation(summary = "Create a new task")
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // PUT /tasks/{id}
    @Operation(summary = "Update a task")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody Task task) {

        Task updatedTask = service.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE /tasks/{id}
    @Operation(summary = "Delete a task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        service.deleteTask(id);

        return ResponseEntity.noContent().build();
    }


}
