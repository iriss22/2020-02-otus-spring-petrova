package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.repository.GenreRepository;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExist;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  public void createGenre(String name) throws AlreadyExist {
    try {
      genreRepository.save(new Genre(name));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExist("genre", e);
      }
      throw e;
    }
  }
}
