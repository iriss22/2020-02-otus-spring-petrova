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
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreTest {

  @Autowired
  private RouterFunction route;

  @Autowired
  private GenreRepository genreRepository;

  private WebTestClient client;

  @Before
  public void init() {
    client = WebTestClient
        .bindToRouterFunction(route)
        .build();
  }

  @Test
  @DisplayName("Проверяю получение жанра")
  public void testGetGenre() {
    Genre genre = new Genre("test genre");
    genreRepository.save(genre).subscribe();

    client.get()
        .uri("/genres")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(Genre.class)
        .contains(genre);
  }

  @Test
  @DisplayName("Проверяю сохранение жанра")
  public void testSaveGenre() {
    String genreName = "test";
    Genre genre = new Genre(genreName);
    client.post()
        .uri("/genres")
        .body(Mono.just(genre), Genre.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Genre.class)
        .equals(genre);

    StepVerifier.create(genreRepository.findByName(genreName))
        .assertNext(savedGenre -> assertThat(savedGenre)
            .hasFieldOrPropertyWithValue("name", genreName)
            .hasFieldOrProperty("id")
        )
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("Проверяю сохранение жанра с одним и тем же именем дважды")
  public void testSaveGenreTwice() {
    String genreName = "test1";
    Genre genre = new Genre(genreName);
    client.post()
        .uri("/genres")
        .body(Mono.just(genre), Genre.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isCreated()
        .expectBody(Genre.class)
        .equals(genre);

    client.post()
        .uri("/genres")
        .body(Mono.just(genre), Genre.class)
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }
}
