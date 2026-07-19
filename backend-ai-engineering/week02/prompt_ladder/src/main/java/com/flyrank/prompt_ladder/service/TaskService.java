package com.flyrank.prompt_ladder.service;

import com.flyrank.prompt_ladder.model.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<Task> tasks = new ArrayList<>();

    public TaskService() {
        tasks.add(new Task(1L, "Learn Spring Boot", false));
        tasks.add(new Task(2L, "Build REST API", true));
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public Task getTaskById(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Task createTask(Task task) {
        task.setId((long) (tasks.size() + 1));
        tasks.add(task);
        return task;
    }

    public Task updateTask(Long id, Task updatedTask) {

        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                task.setTitle(updatedTask.getTitle());
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }

        return null;
    }

    public boolean deleteTask(Long id) {
        return tasks.removeIf(task -> task.getId().equals(id));
    }


}
