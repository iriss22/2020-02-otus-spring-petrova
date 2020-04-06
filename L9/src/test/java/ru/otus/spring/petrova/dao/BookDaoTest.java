package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.dao.book.BookDaoImpl;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Collections;
import java.util.Optional;

@DisplayName("Репозиторий для работы с книгами ")
@DataJpaTest
@Import({BookDaoImpl.class})
public class BookDaoTest {
  private static final long BOOK_ID = 1L;
  private static final long AUTHOR_ID = 1L;
  private static final long GENRE_ID = 1L;
  private static Author firstAuthor;
  private static Genre firstGenre;

  @Autowired
  private BookDaoImpl bookDaoImpl;
  @Autowired
  private TestEntityManager em;

  @BeforeAll
  public static void init() {
    firstAuthor = new Author(AUTHOR_ID, "Фримен");
    firstGenre = new Genre(GENRE_ID, "Информационные технологии");
  }

  @DisplayName("должен получать книгу по идентефикатору")
  @Test
  public void testGetBook() {
    Book book = bookDaoImpl.get(BOOK_ID).get();
    Assert.assertEquals(new Book(BOOK_ID, "Паттерны проектирования", firstAuthor, firstGenre, null), book);
  }

  @DisplayName("должен сохранять книгу ")
  @Test
  public void testSaveBook() {
    Book createdBook = bookDaoImpl.create(new Book("TestBook", firstAuthor, firstGenre));
    Assert.assertNotNull(em.find(Book.class, createdBook.getId()));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке получить не существующую книгу")
  @Test
  public void testGetNotExistedBook() {
    Optional<Book> book = bookDaoImpl.get(999);
    Assert.assertTrue(book.isEmpty());
  }

  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  @Test
  public void testDeleteNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoImpl.delete(999));
  }

  @DisplayName("должен удалять книгу")
  @Test
  public void testDeleteBook() throws DataNotFound {
    Book createdBook = bookDaoImpl.create(new Book("TestBook", firstAuthor, firstGenre));
    long createdBookId = createdBook.getId();
    bookDaoImpl.delete(createdBookId);
    em.detach(createdBook);
    Assert.assertNull(em.find(Book.class, createdBookId));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке обновить не существующую книгу")
  @Test
  public void testUpdateNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookDaoImpl.update(999, "newName"));
  }

  @DisplayName("должен обновлять книгу")
  @Test
  public void testUpdateBook() throws DataNotFound {
    Book createdBook = bookDaoImpl.create(new Book("TestBook", firstAuthor, firstGenre));
    long createdBookId = createdBook.getId();
    String newBookName = "NewName";
    bookDaoImpl.update(createdBookId, newBookName);
    em.detach(createdBook);
    Assertions.assertEquals(new Book(createdBookId, newBookName, firstAuthor, firstGenre, Collections.emptyList()), em.find(Book.class, createdBookId));
  }
}
