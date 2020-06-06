package ru.otus.spring.petrova.exception;

public class AuthorNotFoundException extends DataNotFoundException {

  public AuthorNotFoundException(long id) {
    super("author", id);
  }
}
