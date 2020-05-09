package ru.otus.spring.petrova.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rs/books/{id}/comments")
public class CommentRestController {

  private final CommentService commentService;

  @GetMapping
  public List<CommentDto> getComments(@PathVariable("id") Long bookId) {
    return commentService.getComments(bookId);
  }

  @PostMapping
  public void addComment(@PathVariable("id") Long bookId, CommentDto comment) {
    commentService.createComment(bookId, comment.getText());
  }
}
