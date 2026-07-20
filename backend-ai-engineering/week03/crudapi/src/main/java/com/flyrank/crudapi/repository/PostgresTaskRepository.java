package com.flyrank.crudapi.repository;

import com.flyrank.crudapi.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class PostgresTaskRepository implements TaskRepository {

    private final JpaTaskRepository jpaRepository;

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Task save(Task task) {
        if (task.getId() != null && task.getId() == 0L) {
            task.setId(null);
        }
        return jpaRepository.save(task);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public long nextId() {
        return 0L;
    }

    @Override
    public void resetToSeed() {
        jpaRepository.deleteAll();
        save(new Task(null, "Buy groceries", false));
        save(new Task(null, "Read a book", false));
        save(new Task(null, "Clean the house", true));
    }
}