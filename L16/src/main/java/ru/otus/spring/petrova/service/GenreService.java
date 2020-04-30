package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  @Transactional
  public Genre createGenre(String name) throws AlreadyExistException {
    try {
      return genreRepository.save(new Genre(name));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExistException("genre", e);
      }
      throw e;
    }
  }

  @Transactional
  public Genre getOrCreate(String name) throws AlreadyExistException {
    Optional<Genre> genre = genreRepository.findByName(name);
    if (!genre.isPresent()) {
      return createGenre(name);
    }
    return genre.get();
  }

  public List<Genre> getAll() {
    return genreRepository.findAll();
  }
}
