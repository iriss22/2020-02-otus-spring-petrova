package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.dao.author.AuthorDaoImpl;
import ru.otus.spring.petrova.exception.AlreadyExist;

@DataJpaTest
@DisplayName("Сервис для работы с авторами ")
@Import({AuthorService.class, AuthorDaoImpl.class})
public class AuthorServiceTest {

  @Autowired
  private AuthorService authorService;

  @Test
  @DisplayName("при сохранении одного и того же автора дважды должен выдавать ошибку")
  public void saveAuthorTwice() throws AlreadyExist {
    String authorName = "test";
    authorService.createAuthor(authorName);
    Assertions.assertThrows(AlreadyExist.class, () -> authorService.createAuthor(authorName));
  }
}
