package ru.otus.spring.petrova.dao.author;

import ru.otus.spring.petrova.domain.Author;

public interface AuthorDao {

  long create(Author author);
  Author get(long authorId);
}
