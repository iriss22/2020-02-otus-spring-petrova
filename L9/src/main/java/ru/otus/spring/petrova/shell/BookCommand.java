package ru.otus.spring.petrova.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.petrova.DataNotFound;
import ru.otus.spring.petrova.service.BookService;

@ShellComponent
@RequiredArgsConstructor
public class BookCommand {

  private final BookService bookService;

  @ShellMethod(value = "Add book", key = {"ba", "badd"})
  public String add(@ShellOption String bookName, @ShellOption String authorName, @ShellOption String genre) {
    bookService.addBook(bookName, authorName, genre);
    return "book added";
  }

  @ShellMethod(value = "Update book", key = {"bu", "bupdate"})
  public String update(@ShellOption long bookId, @ShellOption String bookName) {
    try {
      bookService.updateBook(bookId, bookName);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
    return "book updated";
  }

  @ShellMethod(value = "Delete book", key = {"bd", "bdelete"})
  public String delete(@ShellOption long bookId) {
    try {
      bookService.deleteBook(bookId);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
    return "book deleted";
  }

  @ShellMethod(value = "Get book", key = {"bg", "bget"})
  public String get(@ShellOption long bookId) {
    try {
      return bookService.getBook(bookId).toString();
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
  }
}
