package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.Negotiation;
import ru.otus.spring.petrova.domain.UserType;
import ru.otus.spring.petrova.repository.NegotiationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NegotiationService {

  private final NegotiationRepository negotiationRepository;

  public Negotiation save(Negotiation negotiation) {
    return negotiationRepository.save(negotiation);
  }

  public List<Negotiation> getApplicantNegotiationsByVacancyId(Long vacancyId) {
    List<Negotiation> list = negotiationRepository.findByVacancyIdAndAndAuthor(vacancyId, UserType.APPLICANT);
    return list;
  }
}
