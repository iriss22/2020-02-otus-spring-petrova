package ru.otus.spring.petrova.exception;

public class AlreadyExist extends Exception {

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
