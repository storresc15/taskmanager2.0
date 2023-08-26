package com.uradevelopment.springboot.taskmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;

public interface TaskCategoryRepository extends JpaRepository<TaskCategory, Integer> {
}
