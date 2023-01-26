package com.task.service.impl;

import com.task.dao.TaskDaoInterface;
import com.task.exceptions.TaskAlreadyExistsException;
import com.task.exceptions.TaskNotFoundException;
import com.task.model.Task;
import com.task.service.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TaskService implements TaskServiceInterface {


    @Autowired
    TaskDaoInterface taskDao;


    @Override
    public Task createTask(Task task) {
        if(checkIfTaskExists(task.getId())){
            throw new TaskAlreadyExistsException(task.getId());
        }
        return taskDao.createTask(task);
    }

    @Override
    public Collection<Task> returnAllTasks() {
        return taskDao.returnAllTasks();
    }

    @Override
    public Task returnTaskById(Integer id) {
        Task task=taskDao.returnTaskById(id);
        if(task==null)
            throw new TaskNotFoundException(id);
        return task;
    }

    @Override
    public Task updateTask(Task task) {
        checkIfTaskExistsAndErrorIfItDoesNot(task.getId());
        return taskDao.updateTask(task);
    }

    @Override
    public String deleteTaskById(Integer id) {
        checkIfTaskExistsAndErrorIfItDoesNot(id);
        return taskDao.deleteTaskById(id);
    }

    private void checkIfTaskExistsAndErrorIfItDoesNot(Integer id){
        if(!checkIfTaskExists(id)){
            throw new TaskNotFoundException(id);
        }
    }
    private boolean checkIfTaskExists(Integer id){
        if(taskDao.returnTaskById(id)!=null){
          return true;
        }else{
            return false;
        }

    }
}
