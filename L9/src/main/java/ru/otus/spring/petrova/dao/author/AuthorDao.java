package ru.otus.spring.petrova.dao.author;

import ru.otus.spring.petrova.domain.Author;

import java.util.Optional;

public interface AuthorDao {

  Author create(Author author);
  Optional<Author> get(long authorId);
}
