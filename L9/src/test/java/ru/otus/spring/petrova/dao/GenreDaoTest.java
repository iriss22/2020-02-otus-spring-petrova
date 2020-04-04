package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.petrova.dao.genre.GenreDaoJdbc;
import ru.otus.spring.petrova.domain.Genre;

@DisplayName("Репозиторий на основе Jdbc для работы с жанрами ")
@JdbcTest
@Import({GenreDaoJdbc.class})
public class GenreDaoTest {
  private final int GENRE_ID = 1;

  @Autowired
  private GenreDaoJdbc genreDaoJdbc;

  @DisplayName("должен получать жанр по идентефикатору")
  @Test
  public void testGetGenre() {
    Genre genre = genreDaoJdbc.get(GENRE_ID);
    Assert.assertEquals(new Genre(GENRE_ID, "Информационные технологии"), genre);
  }

  @DisplayName("должен сохранять жанр ")
  @Test
  public void testSaveGenre() {
    long createdGenreId = genreDaoJdbc.create(new Genre("TestGenre"));
    Assert.assertEquals(new Genre(createdGenreId, "TestGenre"), genreDaoJdbc.get(createdGenreId));
  }

  @DisplayName("должен выдавать правильную ошибку на не существующего автора")
  @Test
  public void testGetNotExistedGenre() {
    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> genreDaoJdbc.get(999));
  }
}
