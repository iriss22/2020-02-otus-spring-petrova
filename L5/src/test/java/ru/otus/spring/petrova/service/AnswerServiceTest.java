package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.petrova.dao.QuestionDao;
import ru.otus.spring.petrova.domain.AnswerError;
import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.domain.UserLocale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnswerServiceTest {
  @Autowired
  private AnswerService answerService;
  @Autowired
  private TranslationService translationService;

  @MockBean
  private QuestionDao questionDao;

  private static Locale locale = UserLocale.EN.getLocale();

  @BeforeEach
  public void init() {
    List<Question> questions = new ArrayList<>();
    questions.add(new Question("text", 1, "answer1", "answer2", "answer3"));
    questions.add(new Question("text", 2, "answer1", "answer2", "answer3"));
    questions.add(new Question("text", 2, "answer1", "answer2", "answer3"));
    translationService.changeLocale(locale);
    when(questionDao.getQuestions()).thenReturn(questions);
  }

  @Test
  public void checkAnswerTest() {
    List<Integer> userAnswers = Arrays.asList(1, 2, 3);
    int rightAnswerCount = answerService.checkAnswers(userAnswers);
    assertEquals(2, rightAnswerCount);
  }

  @Test
  public void checkValidateMoreThanMax() {
    assertThrows(AnswerError.class, () -> answerService.validate(10, 2));
  }

  @Test
  public void checkValidateLessThanMin() {
    assertThrows(AnswerError.class, () -> answerService.validate(-10, 2));
  }

  @Test
  public void checkValidate() {
    assertDoesNotThrow(() -> answerService.validate(1, 2));
  }
}
