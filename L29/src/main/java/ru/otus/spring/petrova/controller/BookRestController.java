package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rs/books")
public class BookRestController {

  private final BookService bookService;

  @GetMapping
  public List<BookDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  @PostMapping("/{id}")
  public void updateBook(@PathVariable Long id, BookDto book) throws DataNotFound, AlreadyExist {
    bookService.updateBook(id, book.getName());
  }

  @PostMapping
  public void saveBook(BookDto book) throws AlreadyExist {
    bookService.addBook(book.getName(), book.getAuthorName(), book.getGenreName());
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable("id") Long id) throws DataNotFound {
    bookService.deleteBook(id);
  }
}
