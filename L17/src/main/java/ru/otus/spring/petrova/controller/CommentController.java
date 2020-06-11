package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.petrova.dto.CommentDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{id}/comments")
public class CommentController {
  
  @GetMapping
  public String getComments(@PathVariable("id") Long bookId, Model model) {
    model.addAttribute("bookId", bookId);
    return "list_comment";
  }

  @GetMapping("/add")
  public String commentsAdd(@PathVariable("id") Long bookId, Model model) {
    model.addAttribute("bookId", bookId);
    model.addAttribute("comment", new CommentDto(null, ""));
    return "add_comment";
  }
}
