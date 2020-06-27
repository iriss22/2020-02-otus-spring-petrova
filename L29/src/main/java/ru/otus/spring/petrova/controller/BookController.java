package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.spring.petrova.dto.BookDto;
import ru.otus.spring.petrova.exception.AlreadyExist;
import ru.otus.spring.petrova.exception.DataNotFound;
import ru.otus.spring.petrova.service.BookService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @GetMapping("/delete/{id}")
  public String deleteBook(@PathVariable("id") Long id, Model model) throws DataNotFound {
    bookService.deleteBook(id);
    return "redirect:/books";
  }

  @GetMapping("/add")
  public String getEmptyBook(Model model) {
    model.addAttribute("book", new BookDto(null, "", "", ""));
    return "add_book";
  }

  @GetMapping("/{id}")
  public String getBook(@PathVariable("id") Long id, Model model) throws DataNotFound {
    model.addAttribute("book", bookService.getBook(id));
    return "edit_book";
  }

  @GetMapping()
  public String getAllBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "list_book";
  }

  @ExceptionHandler(DataNotFound.class)
  public ModelAndView handleDataNotFound(DataNotFound ex) {
    ModelAndView mav = new ModelAndView();
    mav.setStatus(HttpStatus.NOT_FOUND);
    mav.addObject("text", String.format("Not found %s with id=%s", ex.getName(), ex.getId()));
    mav.setViewName("error");
    return mav;
  }

  @ExceptionHandler(AlreadyExist.class)
  public ModelAndView handleAlreadyExist(AlreadyExist ex) {
    ModelAndView mav = new ModelAndView();
    mav.setStatus(HttpStatus.BAD_REQUEST);
    mav.addObject("text", String.format("Already exist %s", ex.getName()));
    mav.setViewName("error");
    return mav;
  }
}
