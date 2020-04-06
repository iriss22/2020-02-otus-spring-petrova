package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.dao.author.AuthorDao;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExist;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorDao authorDao;

  public void createAuthor(String name) throws AlreadyExist {
    try {
      authorDao.create(new Author(name));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExist("author", e);
      }
      throw e;
    }
  }
}
