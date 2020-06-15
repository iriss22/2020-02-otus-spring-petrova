package ru.otus.spring.petrova.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.petrova.domain.mongo.GenreM;

import java.util.Optional;

public interface GenreRepositoryM extends MongoRepository<GenreM, String> {

  Optional<GenreM> findByName(String name);
}
