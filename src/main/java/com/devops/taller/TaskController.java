package com.devops.taller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final List<Task> tasks = new ArrayList<>();

    public TaskController() {
        tasks.add(new Task(1, "Aprender DevOps con Java", false));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("message", "API REST DevOps Taller Java funcionando correctamente"));
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        if (title == null || title.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El título es requerido"));
        }
        Task newTask = new Task(tasks.size() + 1, title, false);
        tasks.add(newTask);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Map<String, Boolean> body) {
        Optional<Task> taskOpt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (taskOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tarea no encontrada"));
        }
        Task task = taskOpt.get();
        if (body.containsKey("completed")) {
            task.setCompleted(body.get("completed"));
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tarea no encontrada"));
        }
        return ResponseEntity.ok(Map.of("message", "Tarea eliminada exitosamente"));
    }

    public static class Task {
        private int id;
        private String title;
        private boolean completed;

        public Task(int id, String title, boolean completed) {
            this.id = id;
            this.title = title;
            this.completed = completed;
        }

        public int getId() { return id; }
        public String getTitle() { return title; }
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
    }
}
