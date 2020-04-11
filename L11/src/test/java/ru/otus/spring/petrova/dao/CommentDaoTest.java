package ru.otus.spring.petrova.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.dao.comment.CommentDao;
import ru.otus.spring.petrova.dao.comment.CommentDaoImpl;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Comment;

@DisplayName("Репозиторий для работы с комментариями ")
@DataJpaTest
@Import({CommentDaoImpl.class})
public class CommentDaoTest {

  @Autowired
  private CommentDao commentDao;
  @Autowired
  private TestEntityManager em;

  @DisplayName("должен сохранять автора ")
  @Test
  public void testSaveAuthor() {
    Book book = em.find(Book.class, 1L);
    Comment createdComment = commentDao.create(new Comment(book, "TestComment"));
    Assert.assertEquals(em.find(Comment.class, 2L), createdComment);
  }
}
