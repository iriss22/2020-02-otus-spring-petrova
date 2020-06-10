package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/books/{id}/comments")
public class CommentController {

  private final CommentService commentService;

  @GetMapping
  @Secured({"ROLE_ADMIN", "ROLE_USER"})
  public String getComments(@PathVariable("id") Long bookId, Model model) {
    List<CommentDto> comments = commentService.getComments(bookId);
    model.addAttribute("comments", comments);
    model.addAttribute("bookId", bookId);
    return "list_comment";
  }

  @PostMapping
  @Secured("ROLE_USER")
  public String addComment(@PathVariable("id") Long bookId, CommentDto comment) {
    commentService.createComment(bookId, comment.getText());
    return String.format("redirect:/books/%s/comments", bookId);
  }

  @GetMapping("/add")
  @Secured("ROLE_USER")
  public String commentsAdd(@PathVariable("id") Long bookId, Model model) {
    model.addAttribute("bookId", bookId);
    model.addAttribute("comment", new CommentDto(null, ""));
    return "add_comment";
  }
}
