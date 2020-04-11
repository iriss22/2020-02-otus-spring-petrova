package ru.otus.spring.petrova.dao.book;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.exception.BookNotFound;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public Book create(Book book) {
    em.persist(book);
    return book;
  }

  @Override
  public void delete(long id) throws DataNotFound {
    Query query = em.createQuery("delete from Book b where b.id = :id");
    query.setParameter("id", id);
    int cnt = query.executeUpdate();
    if (cnt == 0) {
      throw new BookNotFound(id);
    }
  }

  @Override
  public void update(long id, String name) throws DataNotFound {
    Query query = em.createQuery("update Book b set b.name = :name where b.id = :id");
    query.setParameter("id", id);
    query.setParameter("name", name);
    int cnt = query.executeUpdate();
    if (cnt == 0) {
      throw new BookNotFound(id);
    }
  }

  @Override
  public Optional<Book> get(long id) {
    return Optional.ofNullable(em.find(Book.class, id));
  }
}
