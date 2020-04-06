package ru.otus.spring.petrova.dao.genre;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class GenreDaoImpl implements GenreDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public Genre create(Genre genre) {
    em.persist(genre);
    return genre;
  }

  @Override
  public Optional<Genre> get(long id) {
    return Optional.ofNullable(em.find(Genre.class, id));
  }
}
