package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.dao.TaskOwnerRepository;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskOwnerServiceImpl implements TaskOwnerService {
    private TaskOwnerRepository taskOwnerRepository;

    @Autowired
    public TaskOwnerServiceImpl(TaskOwnerRepository theTaskOwnersRepository) {
        taskOwnerRepository = theTaskOwnersRepository;
    }

    @Override
    public List<TaskOwner> findAll() {
        return taskOwnerRepository.findAll();
    }

    @Override
    public TaskOwner findById(int theId) {
        Optional<TaskOwner> result = taskOwnerRepository.findById(theId);
        TaskOwner theTask;
        if (result.isPresent()) {
            theTask = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find task id - " + theId);
        }
        return theTask;
    }

    @Override
    public TaskOwner findByEmail(String email) {
        return taskOwnerRepository.findByEmail(email);
    }

    @Override
    public void save(TaskOwner theTask) {
        taskOwnerRepository.save(theTask);
    }

    @Override
    public void deleteById(int theId) {
        taskOwnerRepository.deleteById(theId);
    }
}