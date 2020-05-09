package ru.otus.spring.petrova.exception;

import lombok.Data;

@Data
public abstract class DataNotFound extends RuntimeException {
  String name;
  long id;

  public DataNotFound(long id, Exception e) {
    super(e);
    this.id = id;
  }

  public DataNotFound(String name, long id) {
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
