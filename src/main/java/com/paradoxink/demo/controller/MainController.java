package com.paradoxink.demo.controller;

import com.paradoxink.demo.model.Task;
import com.paradoxink.demo.model.User;
import com.paradoxink.demo.service.TaskService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final TaskService taskService;

    public MainController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String mainPage (Model model){

        User currentUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        model.addAttribute("taskList", taskService.findAllByUser(currentUser));

        return "main";
    }

    @GetMapping("/createTask")
    public String createTaskPage (Model model){

        return "createPage";
    }

    //создать - create
    @PostMapping("/newTask")
    public String newTaskPage (@ModelAttribute Task task){

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        taskService.createTask(task, currentUser);
        return "redirect:/";

    }

    //удалить - delete
    @PostMapping("/deleteTask")
    public String deleteTaskPage (@RequestParam("taskId") int taskId){

        User currentUser =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        taskService.deleteTask(taskId, currentUser);
        return "redirect:/";
    }

    // детальный осмотр задачи что бы изменить etc etc
    @GetMapping("/details/{id}")
    public String detailsPage (@PathVariable int id, Model model){

        Task task = taskService.findById(id);
        model.addAttribute("task", task);

        return "details";
    }
    //изменить - update, edit
    @PostMapping("/updateTask")
    public String updateTaskPage (@ModelAttribute Task task){

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        taskService.updateTask(task, currentUser);
        return "redirect:/";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "admin";
    }


}
