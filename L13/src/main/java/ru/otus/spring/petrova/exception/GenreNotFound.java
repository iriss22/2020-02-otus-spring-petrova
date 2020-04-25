package ru.otus.spring.petrova.exception;

public class GenreNotFound extends DataNotFound {

  public GenreNotFound(String id) {
    super("genre", id);
  }
}
