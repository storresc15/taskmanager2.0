package com.uradevelopment.springboot.taskmanager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.uradevelopment.springboot.taskmanager.dao.TaskCategoryRepository;
import com.uradevelopment.springboot.taskmanager.entity.TaskCategory;
import com.uradevelopment.springboot.taskmanager.service.TaskCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskCategoryServiceTest {
    @Mock
    private TaskCategoryRepository taskCategoryRepository;

    @InjectMocks
    private TaskCategoryServiceImpl taskCategoryService;

    private List<TaskCategory> mockCategories;

    @BeforeEach
    public void setup() {
        // Initialize mock categories for testing
        mockCategories = new ArrayList<>();
        mockCategories.add(new TaskCategory());
        mockCategories.add(new TaskCategory());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAll() {
        when(taskCategoryRepository.findAll()).thenReturn(mockCategories);

        List<TaskCategory> categories = taskCategoryService.findAll();

        assertEquals(2, categories.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindById() {
        when(taskCategoryRepository.findById(1)).thenReturn(Optional.of(mockCategories.get(0)));

        TaskCategory category = taskCategoryService.findById(1);

        assertNotNull(category);
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        TaskCategory newCategory = new TaskCategory();

        taskCategoryService.save(newCategory);

        verify(taskCategoryRepository, times(1)).save(newCategory);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteById() {
        int categoryIdToDelete = 2;

        taskCategoryService.deleteById(categoryIdToDelete);

        verify(taskCategoryRepository, times(1)).deleteById(categoryIdToDelete);
    }
}
