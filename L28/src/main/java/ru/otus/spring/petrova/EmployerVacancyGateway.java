package ru.otus.spring.petrova;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.petrova.domain.Negotiation;
import ru.otus.spring.petrova.domain.Vacancy;

import java.util.Collection;

@MessagingGateway(errorChannel = "errorChannel")
public interface EmployerVacancyGateway {

  @Gateway(requestChannel = "vacancyChannel", replyChannel = "negotiationChannel")
  Collection<Negotiation> process(Vacancy vacancy);
}
