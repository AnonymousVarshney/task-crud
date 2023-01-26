package com.task.dao.impl;

import com.task.dao.TaskDaoInterface;
import com.task.model.Task;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class TaskDaoImpl implements TaskDaoInterface {

    ConcurrentHashMap<Integer,Task> taskRepository=new ConcurrentHashMap<>();

    @Override
    public Task createTask(Task task) {

        taskRepository.put(task.getId(), task);
        return task;
    }

    @Override
    public Collection<Task> returnAllTasks() {
        return taskRepository.values();
    }

    @Override
    public Task returnTaskById(Integer id) {
        return taskRepository.get(id);
    }

    @Override
    public Task updateTask(Task task) {
        taskRepository.put(task.getId(),task);
        return task;
    }

    @Override
    public String deleteTaskById(Integer id) {
        taskRepository.remove(id);
        return "success";
    }
}
