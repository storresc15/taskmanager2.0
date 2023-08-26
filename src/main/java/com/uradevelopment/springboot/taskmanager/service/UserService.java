package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.entity.User;

public interface UserService {
    public User findById(int theId);

    public void save(User theUser);
}
