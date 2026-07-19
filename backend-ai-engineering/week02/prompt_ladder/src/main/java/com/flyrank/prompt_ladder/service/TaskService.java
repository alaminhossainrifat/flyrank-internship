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
        tasks.add(new Task(2L, "Complete Assignment", true));

    }

    public List<Task> getAllTasks() {
        return tasks;
    }

}
