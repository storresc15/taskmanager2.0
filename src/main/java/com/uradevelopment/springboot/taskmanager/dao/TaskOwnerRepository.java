package com.uradevelopment.springboot.taskmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;

import java.util.List;

public interface TaskOwnerRepository extends JpaRepository<TaskOwner, Integer>{
    public List<TaskOwner> findAllByOrderByLastNameAsc();

    public TaskOwner findByEmail(String email);

}
