package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AnswerServiceTestPassedTest {
    @Autowired
    private AnswerService answerService;

    @Test
    public void checkTestPassedIfEqual() {
        assertTrue(answerService.isTestPassed(10));
    }

    @Test
    public void checkTestPassedIfMore() {
        assertTrue(answerService.isTestPassed(11));
    }

    @Test
    public void checkTestNotPassedIfLess() {
        assertFalse(answerService.isTestPassed(9));
    }
}
