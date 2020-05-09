package ru.otus.spring.petrova.exception;

import lombok.Data;

@Data
public class AlreadyExist extends RuntimeException {

  String name;

  public AlreadyExist(Exception e) {
    super(e);
  }

  public AlreadyExist(String name, Exception e) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "AlreadyExist{" +
        "name='" + name + '\'' +
        '}';
  }
}
