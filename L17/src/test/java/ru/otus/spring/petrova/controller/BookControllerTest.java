package ru.otus.spring.petrova.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.BookNotFound;
import ru.otus.spring.petrova.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
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
  @DisplayName("должен уметь удалять книгу и возвращать нужный шаблон ")
  public void deleteTest() throws Exception {
    given(bookService.getAllBooks()).willReturn(List.of(BOOK));
    mockMvc.perform(get(String.format("/books/delete/%s", BOOK.getId())))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/books"));
  }

  @Test
  @DisplayName("должен уметь возвращать ошибку, если книга не найдена ")
  public void deleteDataNotFoundTest() throws Exception {
    doThrow(new BookNotFound(BOOK.getId())).when(bookService).deleteBook(BOOK.getId());
    mockMvc.perform(get(String.format("/books/delete/%s", BOOK.getId())))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("должен уметь возвращать пустую заглушку книги и нужный шаблон ")
  public void getEmptyTest() throws Exception {
    mockMvc.perform(get("/books/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("add_book"))
        .andExpect(model().attribute("book", new BookDto(null, "", "", "")));
  }

  @Test
  @DisplayName("должен уметь возвращать книгу и нужный шаблон ")
  public void getTest() throws Exception {
    given(bookService.getBook(anyLong())).willReturn(BOOK);
    mockMvc.perform(get(String.format("/books/%s", BOOK.getId())))
        .andExpect(status().isOk())
        .andExpect(view().name("edit_book"))
        .andExpect(model().attribute("book", BOOK));
  }

  @Test
  @DisplayName("должен уметь возвращать книгу и нужный шаблон ")
  public void getDataNotFoundTest() throws Exception {
    given(bookService.getBook(anyLong())).willThrow(new BookNotFound(BOOK.getId()));
    mockMvc.perform(get(String.format("/books/%s", BOOK.getId())))
        .andExpect(status().isNotFound());
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
}
