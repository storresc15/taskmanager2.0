package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;

import java.util.List;

public interface TaskOwnerService {
    public List<TaskOwner> findAll();

    public TaskOwner findById(int theId);

    public void save(TaskOwner theTask);

    public void deleteById(int theId);

    public TaskOwner findByEmail(String email);
}
