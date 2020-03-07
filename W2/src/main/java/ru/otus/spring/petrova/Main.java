package ru.otus.spring.petrova;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.petrova.service.TestingService;

public class Main {
  public static void main(String[] str) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

    TestingService testingService = context.getBean(TestingService.class);
    testingService.testing();
  }
}
