package ru.otus.spring.petrova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.otus.spring.petrova.config.YamlProps;
import ru.otus.spring.petrova.service.TestingService;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
@EnableAspectJAutoProxy
public class Main {
  public static void main(String[] str) {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, str);

    TestingService testingService = context.getBean(TestingService.class);
    testingService.testing();
  }
}
