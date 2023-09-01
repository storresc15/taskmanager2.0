package com.uradevelopment.springboot.taskmanager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.uradevelopment.springboot.taskmanager.controller.LoginController;
import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.entity.User;
import com.uradevelopment.springboot.taskmanager.service.TaskCategoryService;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerService;
import com.uradevelopment.springboot.taskmanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

@SpringBootTest
public class LoginControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private TaskOwnerService taskOwnerService;

    @Mock
    private TaskCategoryService taskCategoryService;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private Model model;

    @Test
    public void testShowMyLoginPage() {
        String result = loginController.showMyLoginPage();
        assertEquals("login", result);
    }

    @Test
    public void testShowRegistrationForm() {
        String result = loginController.showRegistrationForm(model);
        assertEquals("signup_form", result);
        // Verify the first model.addAttribute(...) call
        verify(model, times(1)).addAttribute(eq("user"), any(User.class));

        // Verify the second model.addAttribute(...) call
        verify(model, times(1)).addAttribute(eq("owner"), any(TaskOwner.class));
    }

    @Test
    public void testProcessRegister() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        TaskOwner owner = new TaskOwner();

        // Perform the action
        String result = loginController.processRegister(user, owner);

        assertEquals("register_success", result);

        // Verify that necessary methods were called with correct arguments
        verify(userService, times(1)).save(user);
        verify(taskOwnerService, times(1)).save(owner);

        // Verify that taskCategoryService.save(...) was invoked, but don't stub it
        verify(taskCategoryService, times(1)).save(any(TaskCategory.class));
    }
}
