package com.flyrank.crudapi_db.repository;

import com.flyrank.crudapi_db.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTaskRepository extends JpaRepository<Task, Long> {
}