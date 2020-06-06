package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExistException;
import ru.otus.spring.petrova.exception.DataNotFoundException;
import ru.otus.spring.petrova.service.BookService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @PostMapping()
  public String saveBook(BookDto book) {
    bookService.addBook(book.getName(), book.getAuthorName(), book.getGenreName());
    return "redirect:/books";
  }

  @PostMapping("/edit/{id}")
  public String updateBook(@PathVariable Long id, BookDto book) {
    bookService.updateBook(id, book.getName());
    return "redirect:/books";
  }

  @GetMapping("/delete/{id}")
  public String deleteBook(@PathVariable("id") Long id, Model model) {
    bookService.deleteBook(id);
    model.addAttribute("books", bookService.getAllBooks());
    return "list_book";
  }

  @GetMapping("/add")
  public String getEmptyBook(Model model) {
    model.addAttribute("book", new BookDto(null, "", "", ""));
    return "add_book";
  }

  @GetMapping("/{id}")
  public String getBook(@PathVariable("id") Long id, Model model) {
    model.addAttribute("book", bookService.getBook(id));
    return "edit_book";
  }

  @GetMapping()
  public String getAllBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "list_book";
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ModelAndView handleDataNotFound(DataNotFoundException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setStatus(HttpStatus.NOT_FOUND);
    mav.addObject("text", String.format("Not found %s with id=%s", ex.getName(), ex.getId()));
    mav.setViewName("error");
    return mav;
  }

  @ExceptionHandler(AlreadyExistException.class)
  public ModelAndView handleAlreadyExist(AlreadyExistException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setStatus(HttpStatus.BAD_REQUEST);
    mav.addObject("text", String.format("Already exist %s", ex.getName()));
    mav.setViewName("error");
    return mav;
  }
}
