package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.DataNotFound;
import ru.otus.spring.petrova.dao.book.BookDaoJdbc;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@JdbcTest
@Import({BookDaoJdbc.class})
public class BookDaoTest {
  private static final int BOOK_ID = 1;
  private static final int AUTHOR_ID = 1;
  private static final int GENRE_ID = 1;
  private static Author firstAuthor;
  private static Genre firstGenre;

  @Autowired
  private BookDaoJdbc bookDaoJdbc;

  @BeforeAll
  public static void init() {
    firstAuthor = new Author(AUTHOR_ID, "Фримен");
    firstGenre = new Genre(GENRE_ID, "Информационные технологии");
  }

  @DisplayName("должен получать книгу по идентефикатору")
  @Test
  public void testGetBook() throws DataNotFound {
    Book book = bookDaoJdbc.get(BOOK_ID);
    Assert.assertEquals(new Book(BOOK_ID, "Паттерны проектирования", firstAuthor, firstGenre), book);
  }

  @DisplayName("должен сохранять книгу ")
  @Test
  public void testSaveBook() throws DataNotFound {
    long createdBookId = bookDaoJdbc.create("TestBook", AUTHOR_ID, GENRE_ID);
    Assert.assertEquals(new Book(createdBookId, "TestBook", firstAuthor, firstGenre), bookDaoJdbc.get(createdBookId));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке получить не существующую книгу")
  @Test
  public void testGetNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoJdbc.get(999));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  @Test
  public void testDeleteNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoJdbc.delete(999));
  }

  @DisplayName("должен удалять книгу")
  @Test
  public void testDeleteBook() throws DataNotFound {
    long deletedBookId = bookDaoJdbc.create("TestBook", AUTHOR_ID, GENRE_ID);
    bookDaoJdbc.delete(deletedBookId);
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoJdbc.get(deletedBookId));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке обновить не существующую книгу")
  @Test
  public void testUpdateNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoJdbc.update(999, "newName"));
  }

  @DisplayName("должен обновлять книгу")
  @Test
  public void testUpdateBook() throws DataNotFound {
    final long bookId = 3;
    bookDaoJdbc.create("TestBook", AUTHOR_ID, GENRE_ID);
    String newBookName = "NewName";
    bookDaoJdbc.update(bookId, newBookName);
    Assertions.assertEquals(new Book(bookId, newBookName, firstAuthor, firstGenre), bookDaoJdbc.get(bookId));
  }
}
