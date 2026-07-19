package com.flyrank.prompt_ladder.controller;

import com.flyrank.prompt_ladder.model.Task;
import com.flyrank.prompt_ladder.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    // Service dependency
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // GET /tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    // GET /tasks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Task task = service.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    // POST /tasks
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task savedTask = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    // PUT /tasks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody Task task) {

        Task updatedTask = service.updateTask(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    // DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        service.deleteTask(id);

        return ResponseEntity.noContent().build();
    }


}
