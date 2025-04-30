package com.example._startcode.service;

import com.example._startcode.entity.Task;
import com.example._startcode.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public void createTasksSequentially(List<String> names) {
        for (String name : names) {
            taskRepository.save(new Task(null, name, false));

            if (name.equalsIgnoreCase("error")) {
                throw new RuntimeException("Lỗi test rollback");
            }
        }
    }

    @Transactional
    public void createTasksInParallel(List<String> names) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (String name : names) {
            tasks.add(() -> {
                taskRepository.save(new Task(null, name, false));
                if (name.equalsIgnoreCase("error")) {
                    throw new RuntimeException("Lỗi song song");
                }
                return null;
            });
        }

        try {
            executor.invokeAll(tasks);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi tổng", e);
        }
    }
}
