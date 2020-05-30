package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.petrova.domain.Comment;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.exception.DataNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Сервис для работы с комментариями к книгам ")
@Import({CommentService.class})
public class CommentServiceTest {

  @Autowired
  private CommentService commentService;

  @Autowired
  private TestEntityManager em;

  private final long BOOK_ID = 1L;

  @Test
  @DisplayName("может сохранить один и тот же комментарий дважды ")
  public void saveCommentTwice() {
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
  public void getCommentsTest() throws DataNotFoundException {
    List<CommentDto> comments = commentService.getComments(BOOK_ID);
    assertThat(comments)
        .isNotNull()
        .hasSize(1);
  }

  @Test
  @DisplayName("должен возвращать ошибку при запросе списка комментариев, если книги не существует ")
  public void getCommentsBadBookTest() {
    List<CommentDto> comments = commentService.getComments(999);
    assertThat(comments).isEmpty();
  }
}
