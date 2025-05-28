package com.paradoxink.demo.controller;

import com.paradoxink.demo.model.Task;
import com.paradoxink.demo.service.TaskService;
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

        model.addAttribute("taskList", taskService.findAll());

        return "main";
    }

    @GetMapping("/createTask")
    public String createTaskPage (Model model){

        return "createPage";
    }

    @PostMapping("/newTask")
    public String newTaskPage (@ModelAttribute Task task){
        taskService.createTask(task);
        return "redirect:/";

    }
    @PostMapping("/deleteTask")
    public String deleteTaskPage (@RequestParam("taskId") int taskId){
        taskService.deleteTask(taskId);
        return "redirect:/";
    }

}
