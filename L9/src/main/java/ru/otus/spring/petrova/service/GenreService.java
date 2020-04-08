package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.dao.genre.GenreDao;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExist;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreDao genreDao;

  public void createGenre(String name) throws AlreadyExist {
    try {
      genreDao.create(new Genre(name));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExist("genre", e);
      }
      throw e;
    }
  }
}
