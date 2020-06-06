package ru.otus.spring.petrova.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.otus.spring.petrova.domain.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {

  Mono<Book> findByName(String name);
}
