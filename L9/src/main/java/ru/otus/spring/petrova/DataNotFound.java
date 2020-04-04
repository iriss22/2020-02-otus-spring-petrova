package ru.otus.spring.petrova;

import lombok.Data;

@Data
public class DataNotFound extends Exception {
  long id;

  public DataNotFound(long id, Exception e) {
    super(e);
    this.id = id;
  }

  public DataNotFound(long id) {
    this.id = id;
  }
}
