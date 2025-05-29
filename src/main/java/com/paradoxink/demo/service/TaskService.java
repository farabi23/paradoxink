package com.paradoxink.demo.service;

import com.paradoxink.demo.enums.TaskStatus;
import com.paradoxink.demo.model.Task;
import com.paradoxink.demo.model.User;
import com.paradoxink.demo.repo.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findAllByUser(User user) {
        return taskRepository.findByUser(user);
    }


    public Task findById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task, User user) {

        Task task1 = new Task();
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setStatus(TaskStatus.TODO); // set default status if needed
        task1.setCreatedAt(LocalDateTime.now());
        task1.setUser(user);

        return taskRepository.save(task1);
    }

    public Task updateTask(Task task, User user) {

        Task existingTask = taskRepository.findById(task.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (existingTask.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("You are not allowed to edit this task");
        }
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedAt(LocalDateTime.now());

        // Don't touch createdAt — keep original

        return taskRepository.save(existingTask);
    }

    public void deleteTask(int id, User user) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getUser() == null || task.getUser().getId() != user.getId()) {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }
        taskRepository.delete(task);
    }




}
