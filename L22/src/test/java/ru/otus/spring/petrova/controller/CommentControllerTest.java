package ru.otus.spring.petrova.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.service.CommentService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CommentController.class)
@WithMockUser(username = "test")
@DisplayName("Контроллер для работы с комментариями к книгам ")
public class CommentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CommentService commentService;

  private static Long BOOK_ID  = 1L;
  private static CommentDto comment;

  @BeforeAll
  public static void init() {
    comment = new CommentDto(BOOK_ID, "good comment");
  }

  @Test
  @DisplayName("должен уметь сохранять комментарий и возвращать нужный шаблон ")
  public void commentsAddTest() throws Exception {
    mockMvc.perform(post(String.format("/books/%s/comments", BOOK_ID), comment.getText()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(String.format("/books/%s/comments", BOOK_ID)));
  }

  @Test
  @DisplayName("должен уметь возвращать пустую заглушку комментария и нужный шаблон ")
  public void addCommentTest() throws Exception {
    mockMvc.perform(get(String.format("/books/%s/comments/add", BOOK_ID), comment.getText()))
        .andExpect(status().isOk())
        .andExpect(view().name("add_comment"))
        .andExpect(model().attribute("bookId", BOOK_ID))
        .andExpect(model().attribute("comment", new CommentDto(null, "")));
  }

  @Test
  @DisplayName("должен уметь возвращать список коментариев к книге и нужный шаблон ")
  public void getAllTest() throws Exception {
    given(commentService.getComments(BOOK_ID)).willReturn(List.of(comment));
    mockMvc.perform(get(String.format("/books/%s/comments", BOOK_ID)))
        .andExpect(status().isOk())
        .andExpect(view().name("list_comment"))
        .andExpect(model().attribute("bookId", BOOK_ID))
        .andExpect(model().attribute("comments", List.of(comment)));
  }
}
