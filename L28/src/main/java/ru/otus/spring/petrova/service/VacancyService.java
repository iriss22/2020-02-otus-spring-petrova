package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.Vacancy;
import ru.otus.spring.petrova.repository.VacancyRepository;

@Service
@RequiredArgsConstructor
public class VacancyService {

  private final VacancyRepository vacancyRepository;

  public Vacancy save(Vacancy vacancy) {
    return vacancyRepository.save(vacancy);
  }

}
