package ru.otus.spring.petrova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.petrova.config.YamlProps;

@SpringBootApplication
@EnableConfigurationProperties(YamlProps.class)
public class Main {
  public static void main(String[] str) {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, str);
  }
}
