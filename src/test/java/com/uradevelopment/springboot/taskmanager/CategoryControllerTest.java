package com.uradevelopment.springboot.taskmanager;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.uradevelopment.springboot.taskmanager.controller.CategoryController;
import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.service.TaskCategoryService;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoryControllerTest {

    @Mock
    private TaskCategoryService categoryService;

    @Mock
    private TaskOwnerService taskOwnerService;

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Test
    void testShowFormForAdd() throws Exception {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser"); // Replace with the desired username

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock necessary data
        TaskOwner owner = new TaskOwner();
        List<TaskCategory> cateogryList = new ArrayList<TaskCategory>();
        TaskCategory category = new TaskCategory();
        cateogryList.add(category);
        owner.setCategories(cateogryList);
        when(taskOwnerService.findByEmail(anyString())).thenReturn(owner);

        // Call the method to be tested
        String result = categoryController.showFormForAdd(model);

        // Verify that the appropriate methods were called
        verify(model).addAttribute(eq("taskCategory"), any(TaskCategory.class));
        verify(model).addAttribute(eq("taskOwner"), eq(owner));
        verify(model).addAttribute(eq("isCreate"), eq(true));

        // Perform assertions on the result
        // For example, you could assert that "result" equals the expected view name
    }

    @Test
    void testShowFormForUpdate() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser"); // Replace with the desired username

        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock necessary data
        List<TaskCategory> cateogryList = new ArrayList<TaskCategory>();
        TaskCategory category = new TaskCategory();
        cateogryList.add(category);
        when(categoryService.findById(anyInt())).thenReturn(category);
        TaskOwner owner = new TaskOwner();
        owner.setCategories(cateogryList);
        when(taskOwnerService.findByEmail(anyString())).thenReturn(owner);

        // Call the method to be tested
        String result = categoryController.showFormForUpdate(1, model);

        // Verify that the appropriate methods were called
        verify(model).addAttribute(eq("taskCategory"), eq(category));
        verify(model).addAttribute(eq("taskOwner"), eq(owner));
        verify(model).addAttribute(eq("isCreate"), eq(false));

        // Perform assertions on the result
        // For example, you could assert that "result" equals the expected view name
    }

    @Test
    void testSaveTaskCategory() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser"); // Replace with the desired username
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock necessary data and set up expectations
        List<TaskCategory> cateogryList = new ArrayList<TaskCategory>();
        TaskCategory taskCategory = new TaskCategory();
        cateogryList.add(taskCategory);
        when(categoryService.findById(anyInt())).thenReturn(taskCategory);
        TaskOwner owner = new TaskOwner();
        owner.setCategories(cateogryList);
        when(taskOwnerService.findByEmail(anyString())).thenReturn(owner);
        //verify(categoryService, times(1)).save(taskCategory);
        //verify(taskOwnerService, times(1)).save(owner);

        // Call the method to be tested
        String result = categoryController.saveTaskCategory(taskCategory, redirectAttributes);

        // Verify that the appropriate methods were called
        //verify(redirectAttributes).addFlashAttribute(eq("errorMessage"), anyString());
        verify(categoryService).save(eq(taskCategory));

        // Perform assertions on the result
        // For example, you could assert that "result" equals the expected redirect path
    }

    @Test
    void testDelete() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("testuser"); // Replace with the desired username
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Mock necessary data and set up expectations
        List<TaskCategory> cateogryList = new ArrayList<TaskCategory>();
        TaskCategory taskCategory = new TaskCategory();
        cateogryList.add(taskCategory);
        TaskOwner owner = new TaskOwner();
        when(categoryService.findById(anyInt())).thenReturn(taskCategory);
        owner.setCategories(cateogryList);
        taskCategory.setOwner(owner);
        when(taskOwnerService.findByEmail(anyString())).thenReturn(owner);

        // Call the method to be tested
        String result = categoryController.delete(1);

        // Verify that the appropriate methods were called
        verify(categoryService).deleteById(eq(1));

        // Perform assertions on the result
        // For example, you could assert that "result" equals the expected redirect path
    }
}
