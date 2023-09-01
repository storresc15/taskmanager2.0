package com.uradevelopment.springboot.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.uradevelopment.springboot.taskmanager.dao.TaskOwnerRepository;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.service.TaskOwnerServiceImpl;
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
public class TaskOwnerServiceTest {
    @Mock
    private TaskOwnerRepository taskOwnerRepository;

    @InjectMocks
    private TaskOwnerServiceImpl taskOwnerService;

    private List<TaskOwner> mockTaskOwners;

    @BeforeEach
    public void setup() {
        // Initialize mock task owners for testing
        mockTaskOwners = new ArrayList<>();
        TaskOwner taskOwner = new TaskOwner();
        taskOwner.setEmail("john@example.com");
        mockTaskOwners.add(taskOwner);
        mockTaskOwners.add(new TaskOwner());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAll() {
        when(taskOwnerRepository.findAll()).thenReturn(mockTaskOwners);

        List<TaskOwner> taskOwners = taskOwnerService.findAll();

        assertEquals(2, taskOwners.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindById() {
        when(taskOwnerRepository.findById(1)).thenReturn(Optional.of(mockTaskOwners.get(0)));

        TaskOwner taskOwner = taskOwnerService.findById(1);

        assertNotNull(taskOwner);
    }

    @Test
    @Transactional
    @Rollback
    public void testFindByEmail() {
        when(taskOwnerRepository.findByEmail("john@example.com")).thenReturn(mockTaskOwners.get(0));

        TaskOwner taskOwner = taskOwnerService.findByEmail("john@example.com");

        assertNotNull(taskOwner);
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        TaskOwner newTaskOwner = new TaskOwner();

        taskOwnerService.save(newTaskOwner);

        verify(taskOwnerRepository, times(1)).save(newTaskOwner);
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteById() {
        int taskOwnerIdToDelete = 2;

        taskOwnerService.deleteById(taskOwnerIdToDelete);

        verify(taskOwnerRepository, times(1)).deleteById(taskOwnerIdToDelete);
    }
}
