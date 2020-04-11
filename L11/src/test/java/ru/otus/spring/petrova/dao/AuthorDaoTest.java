package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.dao.author.AuthorDao;
import ru.otus.spring.petrova.dao.author.AuthorDaoImpl;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExist;

import java.util.Optional;

@DisplayName("Репозиторий для работы с авторами ")
@DataJpaTest
@Import({AuthorDaoImpl.class})
public class AuthorDaoTest {
  private final long AUTHOR_ID = 1;

  @Autowired
  private AuthorDao authorDaoImpl;
  @Autowired
  private TestEntityManager em;

  @DisplayName("должен получать автора по идентефикатору")
  @Test
  public void testGetAuthor() {
    Author author = authorDaoImpl.get(AUTHOR_ID).get();
    Assert.assertEquals(new Author(AUTHOR_ID, "Фримен"), author);
  }

  @DisplayName("должен сохранять автора ")
  @Test
  public void testSaveAuthor() throws AlreadyExist {
    Author createdAuthor = authorDaoImpl.create(new Author( "TestBook"));
    Assert.assertEquals(em.find(Author.class, 2L), createdAuthor);
  }

  @DisplayName("должен выдавать правильную ошибку на не существующего автора")
  @Test
  public void testGetNotExistedAuthor() {
    Optional<Author> author = authorDaoImpl.get(999);
    Assert.assertTrue(author.isEmpty());
  }
}
