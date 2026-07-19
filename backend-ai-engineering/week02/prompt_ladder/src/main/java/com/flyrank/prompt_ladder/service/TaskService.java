package com.flyrank.prompt_ladder.service;

import com.flyrank.prompt_ladder.model.Task;
import org.springframework.stereotype.Service;
import com.flyrank.prompt_ladder.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    // Repository dependency
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    // Retrieve all tasks
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    // Retrieve a task by ID
    public Task getTaskById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Create a new task
    public Task createTask(Task task) {
        return repository.save(task);
    }

    // Update an existing task

    public Task updateTask(Long id, Task task) {

        Task existingTask = repository.findById(id).orElse(null);

        if (existingTask == null) {
            return null;
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setCompleted(task.isCompleted());

        return repository.save(existingTask);
    }

    // Delete a task by ID
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }


}
