package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.repository.AuthorRepository;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExist;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public String createAuthor(String name) throws AlreadyExist {
    Optional<Author> author = authorRepository.findByName(name);
    if (author.isPresent()) {
      throw new AlreadyExist("author");
    }
    return authorRepository.save(new Author(name)).getId();
  }
}
