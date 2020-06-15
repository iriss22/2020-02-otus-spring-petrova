package ru.otus.spring.petrova.repository.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.h2.BookH2;

public interface BookRepositoryH2 extends JpaRepository<BookH2, Long> {

}
