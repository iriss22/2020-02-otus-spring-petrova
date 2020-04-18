package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
