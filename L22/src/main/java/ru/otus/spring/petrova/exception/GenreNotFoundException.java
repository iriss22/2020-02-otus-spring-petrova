package ru.otus.spring.petrova.exception;

public class GenreNotFoundException extends DataNotFoundException {

  public GenreNotFoundException(long id) {
    super("genre", id);
  }
}
