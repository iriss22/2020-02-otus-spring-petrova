package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.petrova.dao.author.AuthorDaoJdbc;
import ru.otus.spring.petrova.domain.Author;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами ")
@JdbcTest
@Import({AuthorDaoJdbc.class})
public class AuthorDaoTest {
  private final int AUTHOR_ID = 1;

  @Autowired
  private AuthorDaoJdbc authorDaoJdbc;

  @DisplayName("должен получать автора по идентефикатору")
  @Test
  public void testGetAddress() {
    Author author = authorDaoJdbc.get(AUTHOR_ID);
    Assert.assertEquals(new Author(AUTHOR_ID, "Фримен"), author);
  }

  @DisplayName("должен сохранять автора ")
  @Test
  public void testSaveAddress() {
    long createdAuthorId = authorDaoJdbc.create(new Author( "TestBook"));
    Assert.assertEquals(new Author(createdAuthorId, "TestBook"), authorDaoJdbc.get(createdAuthorId));
  }

  @DisplayName("должен выдавать правильную ошибку на не существующего автора")
  @Test
  public void testGetNotExistedAuthor() {
    Assertions.assertThrows(EmptyResultDataAccessException.class, () -> authorDaoJdbc.get(999));
  }
}
