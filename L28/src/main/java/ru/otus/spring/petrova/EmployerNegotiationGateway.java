package ru.otus.spring.petrova;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.petrova.domain.Negotiation;
import ru.otus.spring.petrova.domain.Resume;

import java.util.Collection;

@MessagingGateway(errorChannel = "errorChannel")
public interface EmployerNegotiationGateway {

  @Gateway(requestChannel = "negotiationChannel", replyChannel = "resumeChannel")
  Collection<Resume> process(Collection<Negotiation> negotiations);
}
