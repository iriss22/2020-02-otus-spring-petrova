package ru.otus.spring.petrova.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.mongo.BookM;

import java.util.Optional;

public interface BookRepositoryM extends MongoRepository<BookM, String> {

  Optional<BookM> findByName(String name);
}
