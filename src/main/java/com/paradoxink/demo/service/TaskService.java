package com.paradoxink.demo.service;

import com.paradoxink.demo.enums.TaskStatus;
import com.paradoxink.demo.model.Task;
import com.paradoxink.demo.repo.TaskRepository;
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

    public Task findById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {

        Task task1 = new Task();
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setStatus(TaskStatus.TODO); // set default status if needed
        task1.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(task1);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }




}
