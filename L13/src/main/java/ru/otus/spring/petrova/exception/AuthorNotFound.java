package ru.otus.spring.petrova.exception;

public class AuthorNotFound extends DataNotFound {

  public AuthorNotFound(String id) {
    super("author", id);
  }
}
