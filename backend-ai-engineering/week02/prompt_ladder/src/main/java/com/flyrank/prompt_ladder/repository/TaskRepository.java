package com.flyrank.prompt_ladder.repository;

import com.flyrank.prompt_ladder.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository interface for Task entity.
 * Provides built-in CRUD operations.
 */

public interface TaskRepository extends JpaRepository<Task, Long> {

}