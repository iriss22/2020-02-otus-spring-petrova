package ru.otus.spring.petrova.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Genre;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.repository.GenreRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с жанрами ")
@Import({GenreService.class})
public class GenreServiceTest {

  @Autowired
  private GenreService genreService;

  @Autowired
  private GenreRepository genreRepository;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("сохряняет жанр ")
  public void saveGenre() throws AlreadyExistException {
    String name = "TestGenre";
    Genre genre = genreService.createGenre( name);
    Genre savedGenre = em.find(Genre.class, genre.getId());
    assertThat(savedGenre)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при сохранении одного и того же жанра дважды должен выдавать ошибку")
  public void saveGenreTwice() throws AlreadyExistException {
    String genreName = "test";
    genreService.createGenre(genreName);
    Assertions.assertThrows(AlreadyExistException.class, () -> genreService.createGenre(genreName));
  }

  @Test
  @DisplayName("при попытке получить не существующий жанр, он создастся")
  public void testGetOrCreateGenreNotExist() throws AlreadyExistException {
    String name = "not exist genre";
    Optional<Genre> genreBefore = genreRepository.findByName(name);
    Assert.assertTrue(genreBefore.isEmpty());
    Genre genre = genreService.getOrCreate(name);
    assertThat(genre)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
    Optional<Genre> genreAfter = genreRepository.findByName(name);
    assertThat(genreAfter)
        .isNotNull()
        .get()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при попытке получить существующий жанр, он вернется")
  public void testGetOrCreateGenreExist() throws AlreadyExistException {
    String name = "not exist genre";
    em.persist(new Genre(name));
    Optional<Genre> genreBefore = genreRepository.findByName(name);
    assertThat(genreBefore)
        .isNotNull()
        .get()
        .hasFieldOrPropertyWithValue("name", name);
    Genre genre = genreService.getOrCreate(name);
    assertThat(genre)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }
}
