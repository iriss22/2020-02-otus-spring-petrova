package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.dao.genre.GenreDaoImpl;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Optional;

@DisplayName("Репозиторий для работы с жанрами ")
@DataJpaTest
@Import({GenreDaoImpl.class})
public class GenreDaoTest {
  private final int GENRE_ID = 1;

  @Autowired
  private GenreDaoImpl genreDaoImpl;
  @Autowired
  private TestEntityManager em;

  @DisplayName("должен получать жанр по идентефикатору")
  @Test
  public void testGetGenre() {
    Genre genre = genreDaoImpl.get(GENRE_ID).get();
    Assert.assertEquals(new Genre(GENRE_ID, "Информационные технологии"), genre);
  }

  @DisplayName("должен сохранять жанр ")
  @Test
  public void testSaveGenre() {
    Genre createdGenre = genreDaoImpl.create(new Genre("TestGenre"));
    Assert.assertEquals(em.find(Genre.class, 2L), createdGenre);
  }

  @DisplayName("должен выдавать правильную ошибку на не существующего автора")
  @Test
  public void testGetNotExistedGenre() {
    Optional<Genre> genre =  genreDaoImpl.get(999);
    Assert.assertTrue(genre.isEmpty());
  }
}
