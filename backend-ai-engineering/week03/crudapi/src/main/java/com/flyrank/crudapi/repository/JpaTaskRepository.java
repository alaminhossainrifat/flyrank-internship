package com.flyrank.crudapi.repository;

import com.flyrank.crudapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTaskRepository extends JpaRepository<Task, Long> {
}