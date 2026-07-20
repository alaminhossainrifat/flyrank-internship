//package com.flyrank.crudapi_db.config;
//
//import com.flyrank.crudapi_db.model.Task;
//import com.flyrank.crudapi_db.repository.TaskRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//// Automatically populates the SQLite database on startup if it is completely empty.
//@Configuration
//public class DatabaseSeeder {
//
//    @Bean
//    CommandLineRunner initDatabase(TaskRepository repository) {
//        return args -> {
//            // Insert three example tasks only if the table is empty (Stage 0 requirement)
//            if (repository.count() == 0) {
//                repository.saveAll(List.of(
//                        new Task(null, "Buy groceries", false),
//                        new Task(null, "Read a book", false),
//                        new Task(null, "Clean the house", true)
//                ));
//            }
//        };
//    }
//}