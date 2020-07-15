package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.Negotiation;
import ru.otus.spring.petrova.domain.UserType;

import java.util.List;

public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {

  List<Negotiation> findByVacancyIdAndAndAuthor(Long vacancyId, UserType author);
}
