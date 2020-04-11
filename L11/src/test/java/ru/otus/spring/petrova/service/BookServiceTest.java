package ru.otus.spring.petrova.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.dao.author.AuthorDao;
import ru.otus.spring.petrova.dao.author.AuthorDaoImpl;
import ru.otus.spring.petrova.dao.book.BookDaoImpl;
import ru.otus.spring.petrova.dao.genre.GenreDao;
import ru.otus.spring.petrova.dao.genre.GenreDaoImpl;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

@DataJpaTest
@DisplayName("Сервис для работы с книгами ")
@Import({BookService.class, AuthorDaoImpl.class, BookDaoImpl.class, GenreDaoImpl.class})
public class BookServiceTest {

  @Autowired
  private BookService bookService;
  @Autowired
  private AuthorDao authorDao;
  @Autowired
  private GenreDao genreDao;
  @Autowired
  private TestEntityManager em;

  private final String BOOK_NAME = "BookName";
  private final long ID = 2L;
  private Author author;
  private Genre genre;

  @BeforeEach
  public void init() throws AlreadyExist {
    String authorName = "AuthorName";
    String genreName = "GenreName";
    author = authorDao.create(new Author(authorName));
    genre = genreDao.create(new Genre(genreName));
  }

  @Test
  @DisplayName("должен сохранять книгу автора и жанр ")
  public void saveBook() throws DataNotFound, AlreadyExist {
    bookService.addBook(BOOK_NAME, author.getId(), genre.getId());
    Assert.assertEquals(em.find(Book.class, ID), new Book(ID, BOOK_NAME, author, genre, null));
  }

  @Test
  @DisplayName("при сохранении одной и той же книги дважды должен выдавать ошибку")
  public void saveGenreTwice() throws AlreadyExist, DataNotFound {
    bookService.addBook(BOOK_NAME, author.getId(), genre.getId());
    Assertions.assertThrows(AlreadyExist.class, () -> bookService.addBook(BOOK_NAME, author.getId(), genre.getId()));
  }
}
