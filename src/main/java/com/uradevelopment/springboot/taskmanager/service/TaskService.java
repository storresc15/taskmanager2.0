package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.entity.Task;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;

import java.util.List;

public interface TaskService {
    public List<Task> findAll();

    public List<Task> findCompleted(List<Task> tasks);

    public Task findById(int theId);

    public void save(Task theTask);

    public void complete(Task theTask);

    public void deleteById(int theId);

    public List<Task> sortPriorityTasks(TaskOwner theOwner, List<Task> tasks);
}
