package com.uradevelopment.springboot.taskmanager.service;

import com.uradevelopment.springboot.taskmanager.dao.TaskRepository;
import com.uradevelopment.springboot.taskmanager.entity.Task;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService{
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository theTaskRepository) {
        taskRepository = theTaskRepository;
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findById(int theId) {
        Optional<Task> result = taskRepository.findById(theId);
        Task theTask;
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
    public void save(Task theTask) {
        if(theTask.getCreatedDate() == "" || theTask.getCreatedDate() == null) {
            String createdDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            theTask.setCreatedDate(createdDate);
        }
        taskRepository.save(theTask);
    }

    @Override
    public void complete(Task theTask) {
        theTask.setCompleted(true);
        taskRepository.save(theTask);
    }

    @Override
    public void deleteById(int theId) {
        taskRepository.deleteById(theId);
    }

    @Override
    public List<Task> sortPriorityTasks(TaskOwner theOwner, List<Task> tasks) {
        List<Task> sortedTasks = null;
        try {
            PriorityDefinitionStrategy pq = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy(theOwner.getPrioritySelection());
            sortedTasks = pq.setPriority(theOwner, tasks);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sortedTasks;
    }

    @SuppressWarnings("null")
    @Override
    public List<Task> findCompleted(List<Task> tasks) {
        List<Task> completedTasks = null;
        for(Task theTask : tasks) {
            if(theTask.getCompleted() == true) {
                completedTasks.add(theTask);
            }
        }
        return completedTasks;
    }

    //This interface is to be implemented by all the sorting options for priority
    //Use of the Strategy Factory to set the correct strategy based on options
    public interface PriorityDefinitionStrategy {
        List<Task> setPriority(TaskOwner theOwner, List<Task> tasks) throws ParseException, ParseException;
    }
}
