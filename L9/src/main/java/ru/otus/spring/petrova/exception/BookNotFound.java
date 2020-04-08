package ru.otus.spring.petrova.exception;

public class BookNotFound extends DataNotFound {

  public BookNotFound(long id) {
    super("book", id);
  }
}
