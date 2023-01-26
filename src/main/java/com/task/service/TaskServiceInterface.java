package com.task.service;

import com.task.model.Task;

import java.util.Collection;
import java.util.List;

public interface TaskServiceInterface {

    Task createTask(Task task);

    Collection<Task> returnAllTasks();

    Task returnTaskById(Integer id);

    Task updateTask(Task task);

    String deleteTaskById(Integer id);

}
