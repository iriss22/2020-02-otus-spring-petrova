package ru.otus.spring.petrova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Main {

  public static void main(String str[]) {
    SpringApplication.run(Main.class, str);
  }
}
