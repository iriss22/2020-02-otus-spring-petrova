package ru.otus.spring.petrova.repository.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.h2.CommentH2;

import java.util.List;

public interface CommentRepositoryH2 extends JpaRepository<CommentH2, Long> {

  List<CommentH2> findByBookH2_Id(Long bookId);

  void deleteAllByBookH2_Id(Long bookId);
}
