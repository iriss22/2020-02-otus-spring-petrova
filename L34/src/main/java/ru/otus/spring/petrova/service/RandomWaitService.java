package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomWaitService {

  private final static Random r = new Random();

  public void randomWait() {
    if (r.nextBoolean()) {
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {}
    }
  }
}
