package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Comment;
import ru.otus.spring.petrova.exception.DataNotFound;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с комментариями к книгам ")
@Import({BookService.class, CommentService.class})
public class CommentServiceTest {

  @Autowired
  private CommentService commentService;

  @Autowired
  private TestEntityManager em;

  private final long BOOK_ID = 1L;

  @Test
  @DisplayName("может сохранить один и тот же комментарий дважды ")
  public void saveCommentTwice() throws DataNotFound, InterruptedException {
    String comment = "It was great!";
    commentService.createComment(BOOK_ID, comment);
    Comment savedComment = em.find(Comment.class, 2L);
    assertThat(savedComment)
        .isNotNull()
        .hasFieldOrPropertyWithValue("text", comment);
    commentService.createComment(BOOK_ID, comment);
  }

  @Test
  @DisplayName("должен возвращать список комментариев для книги ")
  public void getCommentsTest() throws DataNotFound {
    List<String> comments = commentService.getComments(BOOK_ID);
    assertThat(comments)
        .isNotNull()
        .hasSize(1);
  }

  @Test
  @DisplayName("должен возвращать ошибку при запросе списка комментариев, если книги не существует ")
  public void getCommentsBadBookTest() {
    Assertions.assertThrows(DataNotFound.class, () -> commentService.getComments(999));
  }
}
