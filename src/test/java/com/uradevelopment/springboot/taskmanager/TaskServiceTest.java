package com.uradevelopment.springboot.taskmanager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.uradevelopment.springboot.taskmanager.dao.TaskRepository;
import com.uradevelopment.springboot.taskmanager.entity.Task;
import com.uradevelopment.springboot.taskmanager.entity.TaskOwner;
import com.uradevelopment.springboot.taskmanager.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private List<Task> mockTasks;

    @BeforeEach
    public void setup() {
        // Initialize mock tasks for testing
        mockTasks = new ArrayList<>();
        mockTasks.add(new Task());
        mockTasks.add(new Task());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAll() {
        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> tasks = taskService.findAll();

        assertEquals(2, tasks.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testFindById() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(mockTasks.get(0)));

        Task task = taskService.findById(1);

        assertNotNull(task);
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        Task newTask = new Task();

        taskService.save(newTask);

        verify(taskRepository, times(1)).save(newTask);
    }

    @Test
    @Transactional
    @Rollback
    public void testComplete() {
        Task task = new Task();

        taskService.complete(task);

        verify(taskRepository, times(1)).save(task);
        assertEquals(true, task.getCompleted());
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteById() {
        int taskIdToDelete = 2;

        taskService.deleteById(taskIdToDelete);

        verify(taskRepository, times(1)).deleteById(taskIdToDelete);
    }
/*
    @Test
    public void testSortPriorityTasks() throws ParseException {
        TaskOwner owner = new TaskOwner();
        List<Task> tasks = new ArrayList<>();

        // Mocking PriorityDefinitionStrategy
        TaskServiceImpl.PriorityDefinitionStrategy mockPriorityStrategy = mock(TaskServiceImpl.PriorityDefinitionStrategy.class);
        when(mockPriorityStrategy.setPriority(owner, tasks)).thenReturn(mockTasks);

        List<Task> sortedTasks = taskService.sortPriorityTasks(owner, tasks);

        assertEquals(2, sortedTasks.size());
    }

    @Test
    public void testFindCompleted() {
        Task task1 = new Task();
        task1.setCompleted(true);
        Task task2 = new Task();
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        List<Task> completedTasks = taskService.findCompleted(tasks);

        assertEquals(1, completedTasks.size());
    }*/
}
