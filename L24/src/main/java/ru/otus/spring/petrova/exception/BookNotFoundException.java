package ru.otus.spring.petrova.exception;

public class BookNotFoundException extends DataNotFoundException {

  public BookNotFoundException(long id) {
    super("book", id);
  }
}
