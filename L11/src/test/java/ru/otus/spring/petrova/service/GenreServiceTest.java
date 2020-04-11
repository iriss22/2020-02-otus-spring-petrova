package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExist;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с жанрами ")
@Import({GenreService.class})
public class GenreServiceTest {

  @Autowired
  private GenreService genreService;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("сохряняет жанр ")
  public void saveGenre() throws AlreadyExist {
    String name = "TestGenre";
    genreService.createGenre( name);
    Genre savedGenre = em.find(Genre.class, 2L);
    assertThat(savedGenre)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при сохранении одного и того же жанра дважды должен выдавать ошибку")
  public void saveGenreTwice() throws AlreadyExist {
    String genreName = "test";
    genreService.createGenre(genreName);
    Assertions.assertThrows(AlreadyExist.class, () -> genreService.createGenre(genreName));
  }
}
