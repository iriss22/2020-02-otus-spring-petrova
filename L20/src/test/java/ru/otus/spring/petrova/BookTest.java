package ru.otus.spring.petrova;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.repository.AuthorRepository;
import ru.otus.spring.petrova.repository.BookRepository;
import ru.otus.spring.petrova.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookTest {
  @Autowired
  private RouterFunction route;

  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private GenreRepository genreRepository;
  @Autowired
  private AuthorRepository authorRepository;

  private WebTestClient client;

  private static String genreId;
  private static String authorId;

  @Before
  public void init() {
    client = WebTestClient
        .bindToRouterFunction(route)
        .build();
    genreId = genreRepository.save(new Genre("test genre")).block().getId();
    authorId = authorRepository.save(new Author("test author")).block().getId();
  }

  @Test
  @DisplayName("Проверяю получение книг")
  public void testGetBooks() {
    Book book = new Book("test book1", authorId, genreId);
    bookRepository.save(book).subscribe();

    client.get()
        .uri("/books")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Book.class)
        .contains(book);
  }

  @Test
  @DisplayName("Проверяю получение книги")
  public void testGetBook() {
    Book book = new Book("test book2", authorId, genreId);
    Book savedBook = bookRepository.save(book).block();

    client.get()
        .uri("/books/" + savedBook.getId())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Book.class)
        .equals(book);
  }

  @Test
  @DisplayName("Проверяю сохранение книги")
  public void testSaveBooks() {
    String bookName = "test3";
    Book book = new Book(bookName, authorId, genreId);
    client.post()
        .uri("/books")
        .body(Mono.just(book), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(String.class);

    StepVerifier.create(bookRepository.findByName(bookName))
        .assertNext(savedBook -> assertThat(savedBook)
            .hasFieldOrPropertyWithValue("name", bookName)
            .hasFieldOrProperty("id")
        )
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("Проверяю сохранение книги с плохим автором")
  public void testSaveBookWrongAuthor() {
    String bookName = "test3";
    Book book = new Book(bookName, "", genreId);
    client.post()
        .uri("/books")
        .body(Mono.just(book), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Проверяю сохранение книги с плохим жанром")
  public void testSaveBookWrongGenre() {
    String bookName = "test3";
    Book book = new Book(bookName, authorId, "");
    client.post()
        .uri("/books")
        .body(Mono.just(book), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Проверяю сохранение книги с одним и тем же именем дважды")
  public void testSaveBooksTwice() {
    String bookName = "test4";
    Book book = new Book(bookName, authorId, genreId);

    client.post()
        .uri("/books")
        .body(Mono.just(book), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(String.class);

    client.post()
        .uri("/books")
        .body(Mono.just(book), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Проверяю обновление книги")
  public void testUpdateBooks() {
    Book book = new Book("test book5", authorId, genreId);
    Book savedBook = bookRepository.save(book).block();
    String newBookName = "testNew";
    savedBook.setName(newBookName);

    client.put()
        .uri("/books")
        .body(Mono.just(savedBook), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(Book.class)
        .equals(savedBook);

    StepVerifier.create(bookRepository.findByName(newBookName))
        .assertNext(b -> assertThat(b)
            .hasFieldOrPropertyWithValue("name", newBookName)
            .hasFieldOrProperty("id")
        )
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("Проверяю обновление книги на уже существующую")
  public void testUpdateBookAlreadyExist() {
    String alreadyExistName = "already name";
    Book book = new Book("test book5", authorId, genreId);
    Book savedBook = bookRepository.save(book).block();

    bookRepository.save(new Book(alreadyExistName, authorId, genreId)).subscribe();

    savedBook.setName(alreadyExistName);

    client.put()
        .uri("/books")
        .body(Mono.just(savedBook), Book.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @Test
  @DisplayName("Проверяю удаление книги")
  public void testDeleteBooks() {
    String bookName = "test book6";
    Book book = new Book(bookName, authorId, genreId);
    Book savedBook = bookRepository.save(book).block();

    client.delete()
        .uri("/books/" + savedBook.getId())
        .exchange()
        .expectStatus()
        .isOk();

    StepVerifier.create(bookRepository.findByName(bookName))
        .expectNextCount(0)
        .expectComplete()
        .verify();
  }
}
