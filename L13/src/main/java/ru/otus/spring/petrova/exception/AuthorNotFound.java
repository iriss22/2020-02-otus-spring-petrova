package ru.otus.spring.petrova.exception;

public class AuthorNotFound extends DataNotFound {

  public AuthorNotFound(long id) {
    super("author", id);
  }
}
