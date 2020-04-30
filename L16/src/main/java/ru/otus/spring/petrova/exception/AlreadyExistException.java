package ru.otus.spring.petrova.exception;

import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {

  String name;

  public AlreadyExistException(Exception e) {
    super(e);
  }

  public AlreadyExistException(String name, Exception e) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "AlreadyExist{" +
        "name='" + name + '\'' +
        '}';
  }
}
