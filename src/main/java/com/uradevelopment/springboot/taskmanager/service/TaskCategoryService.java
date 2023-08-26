package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;

import java.util.List;

public interface TaskCategoryService {
    public List<TaskCategory> findAll();

    public TaskCategory findById(int theId);

    public void save(TaskCategory theTaskCategory);

    public void deleteById(int theId);
}
