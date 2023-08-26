package com.uradevelopment.springboot.taskmanager.controller;

import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.entity.User;
import com.uradevelopment.springboot.taskmanager.service.TaskCategoryService;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerService;
import com.uradevelopment.springboot.taskmanager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    private UserService userService;
    private TaskOwnerService taskOwnerService;
    private TaskCategoryService taskCategoryService;

    public LoginController(UserService theUserService, TaskOwnerService theTaskOwnerService, TaskCategoryService theCategoryService) {
        userService = theUserService;
        taskOwnerService = theTaskOwnerService;
        taskCategoryService = theCategoryService;
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("owner", new TaskOwner());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user, TaskOwner owner) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //Add the required values to owner and user
        owner.setEmail(user.getUsername());
        owner.setPrioritySelection("Default");
        owner.setUser(user);
        user.setEnabled(true);
        user.setRole("ROLE_USER");

        userService.save(user);
        taskOwnerService.save(owner);

        //Assigning Default Category for user
        TaskCategory category = new TaskCategory();
        category.setName("Default");
        category.setPercentage(1);
        category.setPercentage(1);
        category.setOwner(owner);

        taskCategoryService.save(category);
        return "register_success";
    }
}
