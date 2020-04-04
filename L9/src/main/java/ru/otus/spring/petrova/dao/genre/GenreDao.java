package ru.otus.spring.petrova.dao.genre;

import ru.otus.spring.petrova.domain.Genre;

public interface GenreDao {

  long create(Genre genre);
  Genre get(long id);
}
