package ru.otus.spring.petrova.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.petrova.domain.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

  Mono<Genre> findByName(String name);
}
