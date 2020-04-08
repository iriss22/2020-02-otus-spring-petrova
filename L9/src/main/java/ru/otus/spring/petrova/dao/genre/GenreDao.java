package ru.otus.spring.petrova.dao.genre;

import ru.otus.spring.petrova.domain.Genre;

import java.util.Optional;

public interface GenreDao {

  Genre create(Genre genre);
  Optional<Genre> get(long id);
}
