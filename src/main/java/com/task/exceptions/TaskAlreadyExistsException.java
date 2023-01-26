package com.task.exceptions;

public class TaskAlreadyExistsException extends RuntimeException {

    public TaskAlreadyExistsException(Integer id) {
        super("Task id already exists please use PUT to update the records: " + id);
    }

}
