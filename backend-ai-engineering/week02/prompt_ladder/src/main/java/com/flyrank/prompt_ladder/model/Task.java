package com.flyrank.prompt_ladder.model;

import jakarta.persistence.*;

/*
 * Entity class representing a Task.
 * Mapped to the "tasks" table in the database.
 */

@Entity
@Table(name = "tasks")
public class Task {

    // Primary key with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Task title
    private String title;

    // Task completion status
    private boolean completed;

    public Task() {
    }

    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}