package ru.otus.spring.petrova.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.mongo.AuthorM;

import java.util.Optional;

public interface AuthorRepositoryM extends MongoRepository<AuthorM, String> {

  Optional<AuthorM> findByName(String name);
}
