package ru.otus.spring.petrova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.Book;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

  Optional<Book> findByName(String name);
}
