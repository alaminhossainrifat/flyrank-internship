package com.flyrank.prompt_ladder.controller;

import com.flyrank.prompt_ladder.model.Task;
import com.flyrank.prompt_ladder.service.TaskService;
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
    public Task getTask(@PathVariable Long id) {
        return service.getTaskById(id);
    }

    // POST /tasks
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return service.createTask(task);
    }

    // PUT /tasks/{id}
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @RequestBody Task task) {
        return service.updateTask(id, task);
    }

    // DELETE /tasks/{id}
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return "Task deleted successfully.";
    }


}
