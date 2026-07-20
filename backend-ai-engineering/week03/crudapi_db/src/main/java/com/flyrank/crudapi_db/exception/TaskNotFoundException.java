package com.flyrank.crudapi_db.exception;

// Exception thrown when a requested task ID is not found in the database.
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(Long id) {
        super("Task not found");
    }
}