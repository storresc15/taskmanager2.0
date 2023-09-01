package com.uradevelopment.springboot.taskmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import com.uradevelopment.springboot.taskmanager.dao.UserRepository;
import com.uradevelopment.springboot.taskmanager.entity.User;
import com.uradevelopment.springboot.taskmanager.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindById() {
        User user = new User();
        user.setId(1);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(1);

        assertEquals(1, foundUser.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void testSave() {
        User user = new User();
        userService.save(user);

        verify(userRepository, times(1)).save(user);
    }

}
