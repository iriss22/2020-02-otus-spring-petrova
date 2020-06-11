package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.petrova.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByBook_Id(Long bookId);

  @Modifying
  @Query("delete from Comment c where c.book.id = ?1")
  void deleteAllByBook_Id(Long bookId);
}
