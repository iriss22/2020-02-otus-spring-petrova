package ru.otus.spring.petrova.dao.author;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class AuthorDaoImpl implements AuthorDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public Author create(Author author) {
      em.persist(author);
      return author;
  }

  @Override
  public Optional<Author> get(long id) {
    return Optional.ofNullable(em.find(Author.class, id));
  }
}
