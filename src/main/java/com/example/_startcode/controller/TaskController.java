package com.example._startcode.controller;

import com.example._startcode.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/sequential")
    public String saveSequential(@RequestBody List<String> names) {
        taskService.createTasksSequentially(names);
        return "OK";
    }

    @PostMapping("/parallel")
    public String saveParallel(@RequestBody List<String> names) {
        taskService.createTasksInParallel(names);
        return "OK";
    }
}