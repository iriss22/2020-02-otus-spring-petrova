package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  @Transactional
  public Author createAuthor(String name) throws AlreadyExistException {
    try {
      return authorRepository.save(new Author(name));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExistException("author", e);
      }
      throw e;
    }
  }

  @Transactional
  public Author getOrCreate(String name) throws AlreadyExistException {
    Optional<Author> author = authorRepository.findByName(name);
    if (!author.isPresent()) {
      return createAuthor(name);
    }
    return author.get();
  }

  public List<Author> getAll() {
    return authorRepository.findAll();
  }
}
