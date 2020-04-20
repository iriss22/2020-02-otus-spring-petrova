package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExist;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import({GenreService.class})
@DisplayName("Сервис для работы с жанрами ")
public class GenreServiceTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private GenreService genreService;

  @Test
  @DisplayName("сохряняет жанр ")
  public void saveGenre() throws AlreadyExist {
    String name = "TestGenre";
    genreService.createGenre( name);
    List<Genre> savedGenre = mongoTemplate.findAll(Genre.class);
    assertThat(savedGenre)
        .isNotNull()
        .hasSize(1)
        .element(0)
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
