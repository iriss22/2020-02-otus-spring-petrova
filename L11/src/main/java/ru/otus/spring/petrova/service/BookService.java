package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.AuthorNotFound;
import ru.otus.spring.petrova.exception.BookNotFound;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.dao.author.AuthorDao;
import ru.otus.spring.petrova.dao.book.BookDao;
import ru.otus.spring.petrova.dao.genre.GenreDao;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.GenreNotFound;

@Service
@RequiredArgsConstructor
public class BookService {

  private final AuthorDao authorDao;
  private final GenreDao genreDao;
  private final BookDao bookDao;

  public void addBook(String bookName, long authorId, long genreId) throws DataNotFound, AlreadyExist {
    Author author = authorDao.get(authorId).orElseThrow(() -> new AuthorNotFound(authorId));
    Genre genre = genreDao.get(genreId).orElseThrow(() -> new GenreNotFound(genreId));

    try {
      bookDao.create(new Book(bookName, author, genre));
    } catch (RuntimeException e) {
      if (e.getCause() instanceof ConstraintViolationException) {
        throw new AlreadyExist("book", e);
      }
      throw e;
    }
  }

  @Transactional
  public void updateBook(long bookId, String bookName) throws DataNotFound {
    bookDao.update(bookId, bookName);
  }

  @Transactional
  public void deleteBook(long id) throws DataNotFound {
    bookDao.delete(id);
  }

  @Transactional(readOnly = true)
  public Book getBook(long id) throws DataNotFound {
    Book book = bookDao.get(id).orElseThrow(() -> new BookNotFound(id));
    return book;
  }

  @Transactional(readOnly = true)
  public String getBookInfo(long id) throws DataNotFound {
    Book book = bookDao.get(id).orElseThrow(() -> new BookNotFound(id));
    return book.toString();
  }
}
