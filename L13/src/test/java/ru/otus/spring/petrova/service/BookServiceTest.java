package ru.otus.spring.petrova.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.petrova.dto.AuthorDto;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.dto.GenreDto;
import ru.otus.spring.petrova.exception.AuthorNotFound;
import ru.otus.spring.petrova.exception.GenreNotFound;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({BookService.class})
@DisplayName("Сервис для работы с книгами ")
public class BookServiceTest {

  @Autowired
  private MongoTemplate mongoTemplate;
  @Autowired
  private BookService bookService;

  private final String BOOK_NAME = "BookName";
  private Author author;
  private Genre genre;

  @BeforeEach
  public void init() {
    String authorName = "AuthorName";
    String genreName = "GenreName";
    author = mongoTemplate.save(new Author(authorName));
    genre = mongoTemplate.save(new Genre(genreName));
  }

  @AfterEach
  public void deleteBook() {
    List<Book> books = mongoTemplate.findAll(Book.class);
    for (Book book : books) {
      mongoTemplate.remove(book);
    }
  }

  private void checkBookInDB(String bookName, Author author, Genre genre) {
    List<Book> books = mongoTemplate.findAll(Book.class);
    assertThat(books)
        .isNotNull()
        .hasSize(1);

    assertThat(books.get(0))
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", bookName)
        .hasFieldOrPropertyWithValue("authorId", author.getId())
        .hasFieldOrPropertyWithValue("genreId", genre.getId());
  }

  @Test
  @DisplayName("должен сохранять книгу ")
  public void saveBook() throws DataNotFound, AlreadyExist {
    bookService.addBook(BOOK_NAME, author.getId(), genre.getId());
    checkBookInDB(BOOK_NAME, author, genre);
  }

  @Test
  @DisplayName("должен выдавать правилльную ошибку при попытке сохранять книгу с несуществующим автором ")
  public void saveBookAndWrongAuthor() {
    Assertions.assertThrows(AuthorNotFound.class, () -> bookService.addBook(BOOK_NAME, "999", genre.getId()));
  }

  @Test
  @DisplayName("должен выдавать правилльную ошибку при попытке сохранять книгу с несуществующим жанром ")
  public void saveBookAndWrongGenre() {
    Assertions.assertThrows(GenreNotFound.class, () -> bookService.addBook(BOOK_NAME, author.getId(), "999"));
  }

  @Test
  @DisplayName("при сохранении одной и той же книги дважды должен выдавать ошибку")
  public void saveBookTwice() throws AlreadyExist, DataNotFound {
    bookService.addBook(BOOK_NAME, author.getId(), genre.getId());
    Assertions.assertThrows(AlreadyExist.class, () -> bookService.addBook(BOOK_NAME, author.getId(), genre.getId()));
  }

  @Test
  @DisplayName("должен обновлять книгу")
  public void testUpdateBook() throws DataNotFound, AlreadyExist {
    Book book = new Book("TestBook", author.getId(), genre.getId());
    mongoTemplate.save(book);
    String newBookName = "NewName";
    bookService.updateBook(book.getId(), newBookName);
    checkBookInDB(newBookName, author, genre);
  }

  @Test
  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  public void testUpdateNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.updateBook("999", "test"));
  }

  @Test
  @DisplayName("должен удалять книгу")
  public void testDeleteBook() throws DataNotFound {
    Book book = new Book("TestBook", author.getId(), genre.getId());
    mongoTemplate.save(book);
    bookService.deleteBook(book.getId());
    List<Book> books = mongoTemplate.findAll(Book.class);
    Assert.assertTrue(books.isEmpty());
  }

  @Test
  @DisplayName("должен выдавать правильную ошибку при попытке удалить не существующую книгу")
  public void testDeleteNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.deleteBook("999"));
  }

  @Test
  @DisplayName("должен получать книгу по идентефикатору")
  public void testGetBook() throws DataNotFound {
    Book book = new Book("TestBook", author.getId(), genre.getId());
    mongoTemplate.save(book);
    BookDto bookDto = bookService.getBook(book.getId());
    Assert.assertEquals(new BookDto(book.getId(), book.getName(), new AuthorDto(author.getName()),
            new GenreDto(genre.getName()), null),
        bookDto);
  }

  @Test
  @DisplayName("должен выдавать правильную ошибку при попытке получить не существующую книгу")
  public void testGetNotExistedBook() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.getBook("999"));
  }

  @Test
  @DisplayName("должен возвращать список комментариев для книги ")
  public void getCommentsTest() throws DataNotFound {
    List<String> initComments = Arrays.asList("Good book", "Bad book");
    Book book = new Book("TestBook", author.getId(), genre.getId());
    book.addComment(initComments.get(0));
    book.addComment(initComments.get(1));
    mongoTemplate.save(book);
    List<String> comments = bookService.getComments(book.getId());
    assertThat(comments)
        .isNotNull()
        .hasSize(2)
        .containsExactlyInAnyOrderElementsOf(initComments);
  }

  @Test
  @DisplayName("должен возвращать ошибку при запросе списка комментариев, если книги не существует ")
  public void getCommentsBadBookTest() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.getComments("999"));
  }

  @Test
  @DisplayName("должен добвлять комментарий для книги ")
  public void addCommentsTest() throws DataNotFound {
    List<String> initComments = Arrays.asList("Good book", "Bad book", "New Comment");
    Book book = new Book("TestBook", author.getId(), genre.getId());
    book.addComment(initComments.get(0));
    book.addComment(initComments.get(1));
    mongoTemplate.save(book);

    bookService.addComment(book.getId(), initComments.get(2));

    List<String> comments = mongoTemplate.findAll(Book.class).get(0).getComments();
    assertThat(comments)
        .isNotNull()
        .hasSize(3)
        .containsExactlyInAnyOrderElementsOf(initComments);
  }

  @Test
  @DisplayName("должен возвращать ошибку при добавлении комментария, если книги не существует ")
  public void addCommentsBadBookTest() {
    Assertions.assertThrows(DataNotFound.class, () -> bookService.addComment("999", "test"));
  }
}
