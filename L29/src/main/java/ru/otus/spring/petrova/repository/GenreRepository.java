package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Optional;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends JpaRepository<Genre, Long> {

  Optional<Genre> findByName(String name);
}
