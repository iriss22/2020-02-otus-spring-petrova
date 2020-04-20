package ru.otus.spring.petrova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

  Optional<Author> findByName(String name);
}
