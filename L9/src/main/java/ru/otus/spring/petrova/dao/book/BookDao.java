package ru.otus.spring.petrova.dao.book;

import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.domain.Book;

import java.util.Optional;

public interface BookDao {

  Book create(Book book);
  void delete(long id) throws DataNotFound;
  void update(long id, String name) throws DataNotFound;
  Optional<Book> get(long id);
}
