package com.task.controller;

import com.task.model.Task;
import com.task.service.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rest/tasks")
public class TaskController {

    @Autowired
    TaskServiceInterface taskService;

    @GetMapping("/getall")
    Collection<Task> findAll() {
        return taskService.returnAllTasks();
    }
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    Task createTask(@RequestBody Task task) {
       return taskService.createTask(task);
    }

    @GetMapping("/get/{id}")
    Task findTask(@PathVariable Integer id) {
        return taskService.returnTaskById(id);
    }

    @PutMapping("/update")
    Task saveOrUpdateTask(@RequestBody Task task) {
        return taskService.updateTask(task);
    }

    @DeleteMapping("/delete/{id}")
    String deleteTask(@PathVariable Integer id) {
        return taskService.deleteTaskById(id);
    }

}
