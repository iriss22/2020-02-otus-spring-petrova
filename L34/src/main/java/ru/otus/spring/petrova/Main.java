package ru.otus.spring.petrova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.petrova.service.HHClientService;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableHystrix
public class Main {
  public static void main(String[] str) {
    ConfigurableApplicationContext context = SpringApplication.run(Main.class, str);
    HHClientService hhClientService = context.getBean(HHClientService.class);
    IntStream.range(0, 10).forEach(i -> System.out.println(hhClientService.getLanguages()));
    System.out.println("The end!");
  }
}
