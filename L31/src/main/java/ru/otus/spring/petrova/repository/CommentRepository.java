package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.petrova.domain.Comment;

import java.util.List;

@RepositoryRestResource(path = "comments")
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByBook_Id(Long bookId);

  void deleteAllByBook_Id(Long bookId);
}
