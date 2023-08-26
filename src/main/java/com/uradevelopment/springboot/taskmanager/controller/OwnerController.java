package com.uradevelopment.springboot.taskmanager.controller;

import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.entity.User;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerService;
import com.uradevelopment.springboot.taskmanager.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    private TaskOwnerService taskOwnerService;
    private UserService userService;

    public OwnerController(TaskOwnerService theTaskOwners, UserService theUserService) {
        taskOwnerService = theTaskOwners;
        userService = theUserService;
    }

    @GetMapping("/showOwnerPreferences")
    public String showOwnerPreferences(@RequestParam("ownerId") int theId, Model theModel) {
        TaskOwner theTaskOwner = taskOwnerService.findById(theId);

        //Get the authenticated Owner
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = auth.getName();

        TaskOwner theAuthOwner = taskOwnerService.findByEmail(currentPrincipalName);

        //Add the Sort Selection for user
        String[] selectionOptions = {
                "Default", "CategoryPercent", "CategoryGrade"
        };

        //Display the categories
        Boolean displayCategories = !theTaskOwner.getPrioritySelection().equals("Default");

        int percent = 0;
        for(TaskCategory tc : theTaskOwner.getCategories()) {
            percent += tc.getPercentage();
        }

        //set task as a model attribute to pre-populate the form
        theModel.addAttribute("taskOwner", theTaskOwner);
        theModel.addAttribute("sortSelection",selectionOptions);
        theModel.addAttribute("categories", theTaskOwner.getCategories());
        theModel.addAttribute("displayCategories", displayCategories);
        theModel.addAttribute("authOwner", theAuthOwner);
        theModel.addAttribute("categoryPercent", percent);

        //send over to our form
        return "task-owners/owner-preferences";
    }

    @PostMapping("/save")
    public String savePreference(@ModelAttribute("taskOwner") TaskOwner theTaskOwner) {
        int userId = theTaskOwner.getUser().getId();

        User theUser = userService.findById(userId);

        theTaskOwner.setUser(theUser);

        //Save the task
        taskOwnerService.save(theTaskOwner);

        //Use a redirect to prevent duplicate submission
        //return "redirect:updateSuccess?ownerId="+theTaskOwner.getId();
        return "redirect:/owner/showOwnerPreferences?ownerId="+theTaskOwner.getId();
    }

    @GetMapping("/updateSuccess")
    public String showSuccessPage(@RequestParam("ownerId") int theId, Model theModel) {
        //send over to our form
        theModel.addAttribute("ownerId", theId);
        return "task-owners/update-success";
    }
}
