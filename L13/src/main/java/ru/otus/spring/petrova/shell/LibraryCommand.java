package ru.otus.spring.petrova.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.service.AuthorService;
import ru.otus.spring.petrova.service.BookService;
import ru.otus.spring.petrova.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommand {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;

  @ShellMethod(value = "Add book", key = {"ba", "badd"})
  public String addBook(@ShellOption String name, @ShellOption String authorId, @ShellOption String genreId) {
    String id;
    try {
      id = bookService.addBook(name, authorId, genreId);
    } catch (DataNotFound | AlreadyExist error) {
      return error.toString();
    }
    return String.format("book %s added", id);
  }

  @ShellMethod(value = "Add author", key = {"aa", "aadd"})
  public String addAuthor(@ShellOption String name) {
    String id;
    try {
      id = authorService.createAuthor(name);
    } catch (AlreadyExist alreadyExist) {
      return alreadyExist.toString();
    }
    return String.format("author %s added", id);
  }

  @ShellMethod(value = "Add genre", key = {"ga", "gadd"})
  public String addGenre(@ShellOption String name) {
    String id;
    try {
      id = genreService.createGenre(name);
    } catch (AlreadyExist alreadyExist) {
      return alreadyExist.toString();
    }
    return String.format("genre %s added", id);
  }

  @ShellMethod(value = "Update book", key = {"bu", "bupdate"})
  public String updateBook(@ShellOption String bookId, @ShellOption String bookName) {
    try {
      bookService.updateBook(bookId, bookName);
    } catch (DataNotFound | AlreadyExist e) {
      return e.toString();
    }
    return "book updated";
  }

  @ShellMethod(value = "Delete book", key = {"bd", "bdelete"})
  public String deleteBook(@ShellOption String bookId) {
    try {
      bookService.deleteBook(bookId);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
    return "book deleted";
  }

  @ShellMethod(value = "Get book", key = {"bg", "bget"})
  public String getBook(@ShellOption String bookId) {
    try {
      return bookService.getBookInfo(bookId);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
  }

  @ShellMethod(value = "Add comment", key = {"ca", "cadd"})
  public String addComment(@ShellOption String bookId, @ShellOption String text) {
    try {
      bookService.addComment(bookId, text);
    } catch (DataNotFound dataNotFound) {
      return dataNotFound.toString();
    }
    return "comment added";
  }

  @ShellMethod(value = "Get comment", key = {"cg", "cget"})
  public String getAllBookComments(@ShellOption String bookId) {
    try {
      return bookService.getComments(bookId).toString();
    } catch (DataNotFound dataNotFound) {
      return dataNotFound.toString();
    }
  }
}
