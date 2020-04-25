package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
