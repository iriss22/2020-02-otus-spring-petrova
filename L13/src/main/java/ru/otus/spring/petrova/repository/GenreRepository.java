package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
