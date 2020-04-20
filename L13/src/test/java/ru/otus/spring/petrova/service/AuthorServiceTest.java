package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExist;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Сервис для работы с авторами ")
@Import({AuthorService.class})
public class AuthorServiceTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private AuthorService authorService;

  @Test
  @DisplayName("сохряняет автора ")
  public void saveAuthor() throws AlreadyExist {
    String name = "TestAuthor";
    authorService.createAuthor(name);
    List<Author> authors = mongoTemplate.findAll(Author.class);
    assertThat(authors)
        .isNotNull()
        .hasSize(1)
        .element(0)
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
