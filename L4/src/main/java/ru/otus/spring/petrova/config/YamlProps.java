package ru.otus.spring.petrova.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
@Data
public class YamlProps {
  private String questionFileName;
  private Integer rightAnswersCount;
}
