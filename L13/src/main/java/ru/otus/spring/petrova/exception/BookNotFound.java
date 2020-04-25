package ru.otus.spring.petrova.exception;

public class BookNotFound extends DataNotFound {

  public BookNotFound(String id) {
    super("book", id);
  }
}
