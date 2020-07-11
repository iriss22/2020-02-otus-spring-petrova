package ru.otus.spring.petrova.actuators;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class UnknownHealthIndicator implements HealthIndicator {

  @Override
  public Health health() {
    return Health.unknown()
        .status(Status.UNKNOWN)
        .withDetail("message", "Я ничего не знаю вообще!")
        .build();
  }
}
