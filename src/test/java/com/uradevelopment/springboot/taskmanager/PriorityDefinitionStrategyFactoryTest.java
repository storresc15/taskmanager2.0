package com.uradevelopment.springboot.taskmanager;

import static org.junit.jupiter.api.Assertions.*;

import com.uradevelopment.springboot.taskmanager.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PriorityDefinitionStrategyFactoryTest {
    @Test
    public void testGetPriorityDefinitionStrategy() {
        TaskServiceImpl.PriorityDefinitionStrategy defaultStrategy = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy("Default");
        TaskServiceImpl.PriorityDefinitionStrategy percentStrategy = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy("CategoryPercent");
        TaskServiceImpl.PriorityDefinitionStrategy gradeStrategy = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy("CategoryGrade");
        TaskServiceImpl.PriorityDefinitionStrategy unknownStrategy = PriorityDefinitionStrategyFactory.getPriorityDefinitionStrategy("Unknown");

        assertTrue(defaultStrategy instanceof PriorityQueue_Utility);
        assertTrue(percentStrategy instanceof PriorityQueue_CategoryPercentage);
        assertTrue(gradeStrategy instanceof PriorityQueue_CategoryGrade);
        assertTrue(unknownStrategy instanceof PriorityQueue_Utility); // Default when unknown
    }
}
