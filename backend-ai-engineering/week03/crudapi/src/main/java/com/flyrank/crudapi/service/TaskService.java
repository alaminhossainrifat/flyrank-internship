package com.flyrank.crudapi.service;

import com.flyrank.crudapi.exception.TaskNotFoundException;
import com.flyrank.crudapi.model.Task;
import com.flyrank.crudapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task create(com.flyrank.crudapi.dto.TaskRequest request) {
        Task task = new Task(
                repository.nextId(),
                request.getTitle(),
                request.getDone() != null && request.getDone()
        );
        return repository.save(task);
    }

    public Task update(Long id, com.flyrank.crudapi.dto.TaskRequest request) {
        Task existing = findById(id); // 404 if missing
        existing.setTitle(request.getTitle());
        existing.setDone(request.getDone() != null ? request.getDone() : existing.isDone());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        repository.deleteById(id);
    }
}

