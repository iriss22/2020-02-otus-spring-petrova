package ru.otus.spring.petrova.repository.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.h2.GenreH2;

import java.util.Optional;

public interface GenreRepositoryH2 extends JpaRepository<GenreH2, Long> {

  Optional<GenreH2> findByName(String name);
}
