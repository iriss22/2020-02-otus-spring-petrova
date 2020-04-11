package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.dao.comment.CommentDao;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Comment;
import ru.otus.spring.petrova.exception.DataNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentDao commentDao;
  private final BookService bookService;

  @Transactional
  public void createComment(long bookId, String name) throws DataNotFound {
    Book book = bookService.getBook(bookId);
    commentDao.create(new Comment(book, name));
  }

  @Transactional(readOnly = true)
  public List<String> getComments(long bookId) throws DataNotFound {
    Book book = bookService.getBook(bookId);
    return book.getComments().stream().map(Comment::getText).collect(Collectors.toList());
  }
}
