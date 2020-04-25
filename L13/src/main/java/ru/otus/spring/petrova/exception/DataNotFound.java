package ru.otus.spring.petrova.exception;

import lombok.Data;

@Data
public abstract class DataNotFound extends Exception {
  String name;
  String id;

  public DataNotFound(String id, Exception e) {
    super(e);
    this.id = id;
  }

  public DataNotFound(String name, String id) {
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
