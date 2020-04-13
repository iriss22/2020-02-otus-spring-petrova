package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExist;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с авторами ")
@Import({AuthorService.class})
public class AuthorServiceTest {

  @Autowired
  private AuthorService authorService;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("сохряняет автора ")
  public void saveAuthor() throws AlreadyExist {
    String name = "TestAuthor";
    authorService.createAuthor( name);
    Author savedAuthor = em.find(Author.class, 2L);
    assertThat(savedAuthor)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при сохранении одного и того же автора дважды должен выдавать ошибку")
  public void saveAuthorTwice() throws AlreadyExist {
    String authorName = "test";
    authorService.createAuthor(authorName);
    Assertions.assertThrows(AlreadyExist.class, () -> authorService.createAuthor(authorName));
  }
}
