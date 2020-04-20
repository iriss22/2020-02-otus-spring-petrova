package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.repository.GenreRepository;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExist;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

  private final GenreRepository genreRepository;

  public String createGenre(String name) throws AlreadyExist {
    Optional<Genre> author = genreRepository.findByName(name);
    if (author.isPresent()) {
      throw new AlreadyExist("genre");
    }
    return genreRepository.save(new Genre(name)).getId();
  }
}
