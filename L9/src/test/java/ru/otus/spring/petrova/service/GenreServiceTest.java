package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.dao.genre.GenreDaoImpl;
import ru.otus.spring.petrova.exception.AlreadyExist;

@DataJpaTest
@DisplayName("Сервис для работы с жанрами ")
@Import({GenreService.class, GenreDaoImpl.class})
public class GenreServiceTest {

  @Autowired
  private GenreService genreService;

  @Test
  @DisplayName("при сохранении одного и того же жанра дважды должен выдавать ошибку")
  public void saveGenreTwice() throws AlreadyExist {
    String genreName = "test";
    genreService.createGenre(genreName);
    Assertions.assertThrows(AlreadyExist.class, () -> genreService.createGenre(genreName));
  }
}
