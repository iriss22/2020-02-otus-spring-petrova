package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.DataNotFound;
import ru.otus.spring.petrova.dao.author.AuthorDao;
import ru.otus.spring.petrova.dao.book.BookDao;
import ru.otus.spring.petrova.dao.genre.GenreDao;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

@Service
@RequiredArgsConstructor
public class BookService {

  private final AuthorDao authorDao;
  private final GenreDao genreDao;
  private final BookDao bookDao;

  @Transactional
  public void addBook(String bookName, String authorName, String genreName) {
    long authorId = authorDao.create(new Author(authorName));
    long genreId = genreDao.create(new Genre(genreName));
    bookDao.create(bookName, authorId, genreId);
  }

  public void updateBook(long bookId, String bookName) throws DataNotFound {
    bookDao.update(bookId, bookName);
  }

  public void deleteBook(long id) throws DataNotFound {
    bookDao.delete(id);
  }

  public Book getBook(long id) throws DataNotFound {
    return bookDao.get(id);
  }
}
