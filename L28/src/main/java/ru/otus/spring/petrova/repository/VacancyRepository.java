package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Vacancy;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
