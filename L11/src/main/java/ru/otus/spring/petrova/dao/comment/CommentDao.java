package ru.otus.spring.petrova.dao.comment;

import ru.otus.spring.petrova.domain.Comment;

public interface CommentDao {
  Comment create(Comment comment);
}
