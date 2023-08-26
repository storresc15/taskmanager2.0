package com.uradevelopment.springboot.taskmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uradevelopment.springboot.taskmanager.entity.Task;
public interface TaskRepository extends JpaRepository<Task, Integer>{
}
