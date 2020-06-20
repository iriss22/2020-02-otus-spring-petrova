package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Comment;
import ru.otus.spring.petrova.dto.CommentDto;
import ru.otus.spring.petrova.repository.CommentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  @Transactional
  public void createComment(long bookId, String comment) {
    commentRepository.save(new Comment(new Book(bookId), comment));
  }

  @Transactional(readOnly = true)
  public List<CommentDto> getComments(long bookId) {
    return commentRepository.findByBook_Id(bookId)
        .stream()
        .map(comment -> new CommentDto(comment.getId(), comment.getText()))
        .collect(Collectors.toList());
  }
}
