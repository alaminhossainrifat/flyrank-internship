package com.flyrank.prompt_ladder.repository;

import com.flyrank.prompt_ladder.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
