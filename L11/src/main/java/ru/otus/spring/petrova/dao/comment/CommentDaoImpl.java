package ru.otus.spring.petrova.dao.comment;

import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentDaoImpl implements CommentDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Comment create(Comment comment) {
    em.persist(comment);
    return comment;
  }
}
