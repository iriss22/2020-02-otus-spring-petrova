package ru.otus.spring.petrova.exception;

public class GenreNotFound extends DataNotFound {

  public GenreNotFound(long id) {
    super("genre", id);
  }
}
