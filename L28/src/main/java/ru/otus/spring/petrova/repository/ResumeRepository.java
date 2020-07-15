package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
