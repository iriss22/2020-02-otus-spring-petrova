package ru.otus.spring.petrova.dao.book;

import ru.otus.spring.petrova.DataNotFound;
import ru.otus.spring.petrova.domain.Book;

public interface BookDao {

  long create(String name, long authorId, long genreId);
  void delete(long id) throws DataNotFound;
  void update(long id, String name) throws DataNotFound;
  Book get(long id) throws DataNotFound;
}
