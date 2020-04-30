package ru.otus.spring.petrova.exception;

import lombok.Data;

@Data
public abstract class DataNotFoundException extends RuntimeException {
  String name;
  long id;

  public DataNotFoundException(long id, Exception e) {
    super(e);
    this.id = id;
  }

  public DataNotFoundException(String name, long id) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    return "DataNotFound{" +
        "name='" + name + '\'' +
        ", id=" + id +
        '}';
  }
}
