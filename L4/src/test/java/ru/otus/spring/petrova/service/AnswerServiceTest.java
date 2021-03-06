package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.petrova.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnswerServiceTest {
  @Autowired
  private AnswerService answerService;

  @Test
  public void checkAnswerTest() {
    List<Integer> userAnswers = Arrays.asList(1, 2, 3);
    List<Question> questions = new ArrayList<>();
    questions.add(new Question("text", 1, "answer1", "answer2", "answer3"));
    questions.add(new Question("text", 2, "answer1", "answer2", "answer3"));
    questions.add(new Question("text", 2, "answer1", "answer2", "answer3"));
    int rightAnswerCount = answerService.checkAnswers(userAnswers, questions);
    assertEquals(2, rightAnswerCount);
  }
}
