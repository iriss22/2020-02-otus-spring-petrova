package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.petrova.Main;
import ru.otus.spring.petrova.domain.UserLocale;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class QuestionServiceSystemTest {

  private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private PrintStream originalOut = System.out;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  private QuestionService getQuestionServiceBean(Locale locale) {
    Locale.setDefault(locale);
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, new String[]{});
    outContent.reset();
    QuestionService questionService = context.getBean(QuestionService.class);
    return questionService;
  }

  @Test
  public void returnRussianResultTest() {
    getQuestionServiceBean(UserLocale.RU.getLocale()).returnResult(2, 3);
    assertEquals(String.format("Ваш результат: 2/3%sВы не прошли тест! Попробуйте еще раз!%s", System.lineSeparator(),
        System.lineSeparator()), outContent.toString());
  }

  @Test
  public void returnEnglishResultTest() {
    getQuestionServiceBean(UserLocale.EN.getLocale()).returnResult(2, 3);
    assertEquals(String.format("Your result: 2/3%sYou failed the test! Try again!%s", System.lineSeparator(),
        System.lineSeparator()), outContent.toString());
  }
}
