package com.uradevelopment.springboot.taskmanager.controller;

import com.uradevelopment.springboot.taskmanager.entity.Task;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerService;
import com.uradevelopment.springboot.taskmanager.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;
    private TaskOwnerService taskOwnerService;

    //constructor injection
    public TaskController(TaskService theTaskService, TaskOwnerService theTaskOwners) {
        taskService = theTaskService;
        taskOwnerService = theTaskOwners;
    }

    @GetMapping("/list")
    public String listTasks(Model theModel) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
        Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");

        List<Task> theTasks = taskService.sortPriorityTasks(theOwner, theOwner.getTasks());

        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        theModel.addAttribute("tasks", theTasks);
        theModel.addAttribute("taskOwner", theOwner);
        theModel.addAttribute("displayCategories",displayCategories);
        theModel.addAttribute("today",today);

        return "tasks/list-tasks";
    }

    @GetMapping("/completedList")
    public String listCompleteTasks(Model theModel) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);
        List<Task> theTasks = taskService.findCompleted(theOwner.getTasks());

        theModel.addAttribute("tasks", theTasks);

        return "tasks/list-complete-tasks";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Task theTask = new Task();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);

        Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");
        Boolean isCreate = true;

        List<TaskCategory> selectionOptions = new ArrayList<TaskCategory>();
        for(TaskCategory tc : theOwner.getCategories()) {
            selectionOptions.add(tc);
        }

        theModel.addAttribute("sortSelection",selectionOptions);
        theModel.addAttribute("task",theTask);
        theModel.addAttribute("displayCategories",displayCategories);
        theModel.addAttribute("taskOwner",theOwner);
        theModel.addAttribute("isCreate",isCreate);

        return "tasks/task-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("taskId") int theId, Model theModel) {
        Task theTask = taskService.findById(theId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);

        Boolean displayCategories = !theOwner.getPrioritySelection().equals("Default");
        Boolean isCreate = false;

        List<TaskCategory> selectionOptions = new ArrayList<TaskCategory>();

        for(TaskCategory tc : theOwner.getCategories()) {
            selectionOptions.add(tc);
        }

        //set task as a model attribute to pre-populate the form
        theModel.addAttribute("sortSelection",selectionOptions);
        theModel.addAttribute("task", theTask);
        theModel.addAttribute("displayCategories",displayCategories);
        theModel.addAttribute("taskOwner",theOwner);
        theModel.addAttribute("isCreate",isCreate);

        //send over to our form
        return "tasks/task-form";
    }

    @PostMapping("/save")
    public String saveTask(@ModelAttribute("task") Task theTask) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();
        TaskOwner theOwner = taskOwnerService.findByEmail(currentPrincipalName);

        if(theTask.getOwner() == null) {
            theTask.setOwner(theOwner);
        }

        if(theTask.getCategory() == null) {
            theTask.setCategory(theOwner.getCategories().get(0));
        }

        //Save the task
        taskService.save(theTask);

        //Use a redirect to prevent duplicate submission
        return "redirect:/tasks/list";
    }

    @GetMapping("/complete")
    public String complete(@RequestParam("taskId") int theId) {
        Task theTask = taskService.findById(theId);

        taskService.complete(theTask);

        return "redirect:/tasks/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("taskId") int theId) {
        taskService.deleteById(theId);

        return "redirect:/tasks/list";
    }
}
