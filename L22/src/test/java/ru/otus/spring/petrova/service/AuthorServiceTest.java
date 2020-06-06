package ru.otus.spring.petrova.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.repository.AuthorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с авторами ")
@Import({AuthorService.class})
public class AuthorServiceTest {

  @Autowired
  private AuthorService authorService;

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private TestEntityManager em;

  @Test
  @DisplayName("сохряняет автора ")
  public void saveAuthor() throws AlreadyExistException {
    String name = "TestAuthor";
    Author createdAuthor = authorService.createAuthor(name);
    Author savedAuthor = em.find(Author.class, createdAuthor.getId());
    assertThat(savedAuthor)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при сохранении одного и того же автора дважды должен выдавать ошибку")
  public void saveAuthorTwice() throws AlreadyExistException {
    String authorName = "test";
    authorService.createAuthor(authorName);
    Assertions.assertThrows(AlreadyExistException.class, () -> authorService.createAuthor(authorName));
  }

  @Test
  @DisplayName("при попытке получить не существующего автора, он создастся")
  public void testGetOrCreateAuthorNotExist() throws AlreadyExistException {
    String name = "not exist author";
    Optional<Author> authorBefore = authorRepository.findByName(name);
    Assert.assertTrue(authorBefore.isEmpty());
    Author author = authorService.getOrCreate(name);
    assertThat(author)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
    Optional<Author> authorAfter = authorRepository.findByName(name);
    assertThat(authorAfter)
        .isNotNull()
        .get()
        .hasFieldOrPropertyWithValue("name", name);
  }

  @Test
  @DisplayName("при попытке получить существующего автора, он вернется")
  public void testGetOrCreateAuthorExist() throws AlreadyExistException {
    String name = "not exist author";
    em.persist(new Author(name));
    Optional<Author> authorBefore = authorRepository.findByName(name);
    assertThat(authorBefore)
        .isNotNull()
        .get()
        .hasFieldOrPropertyWithValue("name", name);
    Author author = authorService.getOrCreate(name);
    assertThat(author)
        .isNotNull()
        .hasFieldOrPropertyWithValue("name", name);
  }
}
