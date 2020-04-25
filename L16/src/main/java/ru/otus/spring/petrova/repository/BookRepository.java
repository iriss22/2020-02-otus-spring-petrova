package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
