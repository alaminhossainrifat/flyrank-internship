package com.flyrank.prompt_ladder.service;

import com.flyrank.prompt_ladder.model.Task;
import org.springframework.stereotype.Service;
import com.flyrank.prompt_ladder.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }

    public Task updateTask(Long id, Task task) {

        Task existingTask = repository.findById(id).orElse(null);

        if (existingTask == null) {
            return null;
        }

        existingTask.setTitle(task.getTitle());
        existingTask.setCompleted(task.isCompleted());

        return repository.save(existingTask);
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
    }


}
