package com.flyrank.crudapi_db.service;

import com.flyrank.crudapi_db.dto.TaskRequest;
import com.flyrank.crudapi_db.exception.TaskNotFoundException;
import com.flyrank.crudapi_db.model.Task;
import com.flyrank.crudapi_db.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Business logic layer interacting with the TaskRepository.
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    // Retrieves all tasks from the database.
    public List<Task> findAll() {
        return repository.findAll();
    }

    // Retrieves a single task by ID or throws an exception if not found.
    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    // Creates and saves a new task in the database.
    public Task create(TaskRequest request) {
        Task task = new Task(
                null,
                request.getTitle(),
                request.getDone() != null && request.getDone()
        );
        return repository.save(task);
    }

    // Updates an existing task and saves the changes.
    public Task update(Long id, TaskRequest request) {
        Task existing = findById(id);
        existing.setTitle(request.getTitle());
        if (request.getDone() != null) {
            existing.setDone(request.getDone());
        }
        return repository.save(existing);
    }

    // Deletes a task from the database by its ID.
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
