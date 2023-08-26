package com.uradevelopment.springboot.taskmanager.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uradevelopment.springboot.taskmanager.entity.User;
public interface UserRepository extends JpaRepository<User, Integer>{
}
