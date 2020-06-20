package ru.otus.spring.petrova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.exception.BookNotFoundException;
import ru.otus.spring.petrova.security.CustomUserDetailsService;
import ru.otus.spring.petrova.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
@WithMockUser(username = "test")
@DisplayName("Контроллер для работы с книгами ")
public class BookControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  private static BookDto BOOK;

  @BeforeAll
  public static void init() {
    BOOK = new BookDto(1L, "good book", "author", "genre");
  }

  @Test
  @DisplayName("должен уметь сохранять книги и возвращать нужный шаблон ")
  public void saveTest() throws Exception {
    given(bookService.addBook(BOOK.getName(), BOOK.getAuthorName(), BOOK.getGenreName())).willReturn(BOOK);
    mockMvc.perform(post("/books")
        .param("name", BOOK.getName())
        .param("authorName", BOOK.getAuthorName())
        .param("genreName", BOOK.getGenreName())
        .param("id", BOOK.getId().toString()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/books"));
  }

  @Test
  @DisplayName("должен уметь возвращать ошибку, если книга уже существует ")
  public void saveAlreadyExistTest() throws Exception {
    given(bookService.addBook(BOOK.getName(), BOOK.getAuthorName(), BOOK.getGenreName()))
        .willThrow(new AlreadyExistException("book", new Exception()));
    mockMvc.perform(post("/books")
        .param("name", BOOK.getName())
        .param("authorName", BOOK.getAuthorName())
        .param("genreName", BOOK.getGenreName())
        .param("id", BOOK.getId().toString()))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("должен уметь обновлять книгу и возвращать нужный шаблон ")
  public void updateTest() throws Exception {
    String newName = "new name";
    given(bookService.updateBook(anyLong(), any())).willReturn(BOOK);
    mockMvc.perform(post("/books/edit/{id}", BOOK.getId())
        .param("name", newName)
        .param("authorName", BOOK.getAuthorName())
        .param("genreName", BOOK.getGenreName())
        .param("id", BOOK.getId().toString()))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/books"));
  }

  @Test
  @DisplayName("должен уметь возвращать ошибку, если книга с таким именем уже есть")
  public void updateAlreadyExistTest() throws Exception {
    String newName = "new name";
    given(bookService.updateBook(anyLong(), any())).willThrow(new AlreadyExistException("book", new Exception()));
    mockMvc.perform(post(String.format("/books/edit/%s", BOOK.getId()))
        .content(new ObjectMapper().writeValueAsString(new BookDto(BOOK.getId(), newName, BOOK.getAuthorName(), BOOK.getGenreName())))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("должен уметь возвращать ошибку, если книга не найдена ")
  public void updateNotFoundTest() throws Exception {
    String newName = "new name";
    given(bookService.updateBook(anyLong(), any())).willThrow(new BookNotFoundException(BOOK.getId()));
    mockMvc.perform(post(String.format("/books/edit/%s", BOOK.getId()))
        .content(new ObjectMapper().writeValueAsString(new BookDto(BOOK.getId(), newName, BOOK.getAuthorName(), BOOK.getGenreName())))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("должен уметь удалять книгу и возвращать нужный шаблон ")
  @WithMockUser(
      username = "test",
      authorities = {"ROLE_ADMIN"}
  )
  public void deleteTest() throws Exception {
    given(bookService.getAllBooks()).willReturn(List.of(BOOK));
    mockMvc.perform(get(String.format("/books/delete/%s", BOOK.getId())))
        .andExpect(status().isOk())
        .andExpect(view().name("list_book"))
        .andExpect(model().attribute("books", List.of(BOOK)));
  }

  @Test
  @DisplayName("должен уметь возвращать ошибку, если книга не найдена ")
  @WithMockUser(
      username = "test",
      authorities = {"ROLE_ADMIN"}
  )
  public void deleteDataNotFoundTest() throws Exception {
    doThrow(new BookNotFoundException(BOOK.getId())).when(bookService).deleteBook(BOOK.getId());
    mockMvc.perform(get(String.format("/books/delete/%s", BOOK.getId())))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("должен уметь возвращать пустую заглушку книги и нужный шаблон ")
  @WithMockUser(
      username = "test",
      authorities = {"ROLE_ADMIN"}
  )
  public void getEmptyTest() throws Exception {
    mockMvc.perform(get("/books/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("add_book"))
        .andExpect(model().attribute("book", new BookDto(null, "", "", "")));
  }

  @Test
  @DisplayName("должен уметь возвращать книгу и нужный шаблон ")
  @WithMockUser(
      username = "test",
      authorities = {"ROLE_ADMIN"}
  )
  public void getTest() throws Exception {
    given(bookService.getBook(anyLong())).willReturn(BOOK);
    mockMvc.perform(get(String.format("/books/%s", BOOK.getId())))
        .andExpect(status().isOk())
        .andExpect(view().name("edit_book"))
        .andExpect(model().attribute("book", BOOK));
  }

  @Test
  @DisplayName("должен уметь возвращать список книг и нужный шаблон ")
  public void getAllTest() throws Exception {
    given(bookService.getAllBooks()).willReturn(List.of(BOOK));
    mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
        .andExpect(view().name("list_book"))
        .andExpect(model().attribute("books", List.of(BOOK)));
  }

  @Test
  @DisplayName("должен уметь возвращать книгу и нужный шаблон ")
  @WithMockUser(
      username = "test",
      authorities = {"ROLE_ADMIN"}
  )
  public void getDataNotFoundTest() throws Exception {
    given(bookService.getBook(anyLong())).willThrow(new BookNotFoundException(BOOK.getId()));
    mockMvc.perform(get(String.format("/books/%s", BOOK.getId())))
        .andExpect(status().isNotFound());
  }
}
