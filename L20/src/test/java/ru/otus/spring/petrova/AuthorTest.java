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
import ru.otus.spring.petrova.repository.AuthorRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorTest {

  @Autowired
  private RouterFunction route;

  @Autowired
  private AuthorRepository authorRepository;

  private WebTestClient client;

  @Before
  public void init() {
    client = WebTestClient
        .bindToRouterFunction(route)
        .build();
  }

  @Test
  @DisplayName("Проверяю получение автора")
  public void testGetAuthors() {
    Author author = new Author("test author");
    authorRepository.save(author).subscribe();

    client.get()
        .uri("/authors")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Author.class)
        .contains(author);
  }

  @Test
  @DisplayName("Проверяю сохранение автора")
  public void testSaveAuthors() {
    String authorName = "test";
    Author author = new Author(authorName);
    client.post()
        .uri("/authors")
        .body(Mono.just(author), Author.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Author.class)
        .equals(author);

    StepVerifier.create(authorRepository.findByName(authorName))
        .assertNext(savedAuthor -> assertThat(savedAuthor)
            .hasFieldOrPropertyWithValue("name", authorName)
            .hasFieldOrProperty("id")
        )
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("Проверяю сохранение автора с одним и тем же именем дважды")
  public void testSaveAuthorsTwice() {
    String authorName = "test1";
    Author author = new Author(authorName);
    client.post()
        .uri("/authors")
        .body(Mono.just(author), Author.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Author.class)
        .equals(author);

    client.post()
        .uri("/authors")
        .body(Mono.just(author), Author.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }
}
