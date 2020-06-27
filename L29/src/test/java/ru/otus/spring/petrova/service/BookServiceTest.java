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
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Comment;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.repository.AuthorRepository;
import ru.otus.spring.petrova.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с книгами ")
@Import({BookService.class, AuthorService.class, GenreService.class})
public class BookServiceTest {

  @Autowired
  private BookService bookService;
  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private GenreRepository genreRepository;
  @Autowired
  private TestEntityManager em;

  private final String BOOK_NAME = "BookName";
  private static final long AUTHOR_ID = 1L;
  private static final long GENRE_ID = 1L;
  private static final long BOOK_ID = 1L;
  private Author author;
  private Genre genre;
  private static Author firstAuthor;
  private static Genre firstGenre;

  @BeforeEach
  public void init() {
    String authorName = "AuthorName";
    String genreName = "GenreName";
    author = authorRepository.save(new Author(authorName));
    genre = genreRepository.save(new Genre(genreName));

    firstAuthor = new Author(AUTHOR_ID, "Фримен");
    firstGenre = new Genre(GENRE_ID, "Информационные технологии");
  }

  private void checkBook(Book book, String bookName, Author author, Genre genre) {
    assertThat(book)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", bookName)
        .hasFieldOrPropertyWithValue("author", author)
        .hasFieldOrPropertyWithValue("genre", genre);
  }

  @Test
  @DisplayName("должен сохранять книгу, автора и жанр ")
  public void saveBook() throws AlreadyExist {
    BookDto book = bookService.addBook(BOOK_NAME, author.getName(), genre.getName());
    Book savedBook = em.find(Book.class, book.getId());
    checkBook(savedBook, BOOK_NAME, author, genre);
  }

  @Test
  @DisplayName("при сохранении одной и той же книги дважды должен выдавать ошибку")
  public void saveBookTwice() throws AlreadyExist, DataNotFound {
    bookService.addBook(BOOK_NAME, author.getName(), genre.getName());
    Assertions.assertThrows(AlreadyExist.class, () -> bookService.addBook(BOOK_NAME, author.getName(), genre.getName()));
  }

  @DisplayName("должен обновлять книгу")
  @Test
  public void testUpdateBook() throws DataNotFound, AlreadyExist {
    Book book = new Book("TestBook", author, genre);
    em.persist(book);
    em.flush();
    String newBookName = "NewName";
    bookService.updateBook(book.getId(), newBookName);
    em.flush();
    Book updatedBook = em.find(Book.class, book.getId());
    checkBook(updatedBook, newBookName, author, genre);
  }

  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  @Test
  public void testUpdateNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.updateBook(999L, "test"));
  }

  @DisplayName("должен удалять книгу")
  @Test
  public void testDeleteBook() throws DataNotFound {
    Book book = new Book("TestBook", author, genre);
    em.persist(book);
    em.flush();
    bookService.deleteBook(book.getId());
    em.flush();
    Assert.assertNull(em.find(Book.class, book.getId()));
  }

  @DisplayName("должен удалять книгу с комментариями ")
  @Test
  public void testDeleteBookWithComments() throws DataNotFound {
    Book book = new Book("TestBook", author, genre);
    em.persist(book);
    Comment comment = new Comment(book, "comment");
    em.persist(comment);
    em.flush();
    bookService.deleteBook(book.getId());
    em.flush();
    Assert.assertNull(em.find(Book.class, book.getId()));
  }

  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  @Test
  public void testDeleteNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.deleteBook(999L));
  }

  @DisplayName("должен получать книгу по идентефикатору")
  @Test
  public void testGetBook() throws DataNotFound {
    BookDto book = bookService.getBook(BOOK_ID);
    Assert.assertEquals(new BookDto(BOOK_ID, "Паттерны проектирования", firstAuthor.getName(), firstGenre.getName()), book);
  }

  @DisplayName("должен выдавать правильную ошибку при попытке получить не существующую книгу")
  @Test
  public void testGetNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.getBook(999L));
  }
}
