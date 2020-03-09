package ru.otus.spring.petrova;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.petrova.service.TestingService;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
@PropertySource("classpath:application.properties")
public class Main {
  public static void main(String[] str) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

    TestingService testingService = context.getBean(TestingService.class);
    testingService.testing();
  }
}
