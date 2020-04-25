package ru.otus.spring.petrova.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {

  Optional<Genre> findByName(String name);
}
