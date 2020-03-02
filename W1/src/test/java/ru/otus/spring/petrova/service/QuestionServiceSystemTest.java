package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionServiceSystemTest {

  private QuestionService questionService;
  private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final static PrintStream originalOut = System.out;

  @BeforeAll
  public static void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @AfterAll
  public static void restoreStreams() {
    System.setOut(originalOut);
  }

  public QuestionServiceSystemTest() {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
    questionService = context.getBean(QuestionService.class);
  }

  @Test
  public void returnResultTest() {
    questionService.returnResult(2, 3);
    assertEquals("your result: 2/3", outContent.toString());
  }
}
