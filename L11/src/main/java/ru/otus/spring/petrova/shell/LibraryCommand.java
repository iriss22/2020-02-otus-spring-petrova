package ru.otus.spring.petrova.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.service.AuthorService;
import ru.otus.spring.petrova.service.BookService;
import ru.otus.spring.petrova.service.CommentService;
import ru.otus.spring.petrova.service.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class LibraryCommand {

  private final BookService bookService;
  private final AuthorService authorService;
  private final GenreService genreService;
  private final CommentService commentService;

  @ShellMethod(value = "Add book", key = {"ba", "badd"})
  public String addBook(@ShellOption String name, @ShellOption long authorId, @ShellOption long genreId) {
    try {
      bookService.addBook(name, authorId, genreId);
    } catch (DataNotFound | AlreadyExist error) {
      return error.toString();
    }
    return "book added";
  }

  @ShellMethod(value = "Add author", key = {"aa", "aadd"})
  public String addAuthor(@ShellOption String name) {
    try {
      authorService.createAuthor(name);
    } catch (AlreadyExist alreadyExist) {
      return alreadyExist.toString();
    }
    return "author added";
  }

  @ShellMethod(value = "Add genre", key = {"ga", "gadd"})
  public String addGenre(@ShellOption String name) {
    try {
      genreService.createGenre(name);
    } catch (AlreadyExist alreadyExist) {
      return alreadyExist.toString();
    }
    return "genre added";
  }

  @ShellMethod(value = "Update book", key = {"bu", "bupdate"})
  public String updateBook(@ShellOption long bookId, @ShellOption String bookName) {
    try {
      bookService.updateBook(bookId, bookName);
    } catch (DataNotFound | AlreadyExist e) {
      return e.toString();
    }
    return "book updated";
  }

  @ShellMethod(value = "Delete book", key = {"bd", "bdelete"})
  public String deleteBook(@ShellOption long bookId) {
    try {
      bookService.deleteBook(bookId);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
    return "book deleted";
  }

  @ShellMethod(value = "Get book", key = {"bg", "bget"})
  public String getBook(@ShellOption long bookId) {
    try {
      return bookService.getBookInfo(bookId);
    } catch (DataNotFound dataNotFound) {
      return String.format("book with id %s not found", bookId);
    }
  }

  @ShellMethod(value = "Add comment", key = {"ca", "cadd"})
  public String addComment(@ShellOption long bookId, @ShellOption String text) {
    try {
      commentService.createComment(bookId, text);
    } catch (DataNotFound dataNotFound) {
      return dataNotFound.toString();
    }
    return "comment added";
  }

  @ShellMethod(value = "Get comment", key = {"cg", "cget"})
  public String getAllBookComments(@ShellOption long bookId) {
    try {
      return commentService.getComments(bookId).toString();
    } catch (DataNotFound dataNotFound) {
      return dataNotFound.toString();
    }
  }
}
