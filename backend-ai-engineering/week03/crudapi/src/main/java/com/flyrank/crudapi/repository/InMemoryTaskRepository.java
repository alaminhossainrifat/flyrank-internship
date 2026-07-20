package com.flyrank.crudapi.repository;

import com.flyrank.crudapi.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public InMemoryTaskRepository() {
        resetToSeed();
    }

    @Override
    public List<Task> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Task save(Task task) {
        store.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public long nextId() {
        return idGenerator.incrementAndGet();
    }

    // Restores the 3 example tasks. Used at startup and by the optional /reset endpoint.
    @Override
    public void resetToSeed() {
        store.clear();
        idGenerator.set(0);
        save(new Task(nextId(), "Buy groceries", false));
        save(new Task(nextId(), "Read a book", false));
        save(new Task(nextId(), "Clean the house", true));
    }
}

