package ru.otus.spring.petrova;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessagingException;
import ru.otus.spring.petrova.domain.Negotiation;
import ru.otus.spring.petrova.domain.Resume;
import ru.otus.spring.petrova.domain.UserType;
import ru.otus.spring.petrova.domain.Vacancy;
import ru.otus.spring.petrova.service.NegotiationService;
import ru.otus.spring.petrova.service.ResumeService;
import ru.otus.spring.petrova.service.VacancyService;

import java.util.Collection;
import java.util.Optional;

@SpringBootApplication
public class Main {

  @Bean
  public QueueChannel errorChannel() {
    return MessageChannels.queue(100).get();
  }

  @Bean
  public QueueChannel negotiationChannel() {
    return MessageChannels.queue(100).get();
  }

  @Bean
  public QueueChannel resumeChannel() {
    return MessageChannels.queue().get();
  }

  @Bean
  public DirectChannel vacancyChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(name = PollerMetadata.DEFAULT_POLLER)
  public PollerMetadata poller() {
    return Pollers.fixedRate(100).maxMessagesPerPoll(2).get() ;
  }

  @Bean
  public IntegrationFlow negotiationFlow() {
    return IntegrationFlows.from("negotiationChannel")
        .split()
        .transform(Negotiation.class, Negotiation::getResumeId)
        .handle("resumeService", "getById")
        .filter(Optional.class, resume -> resume.isPresent())
        .transform(Optional.class, resume -> resume.get())
        .aggregate()
        .get();
  }

  @Bean
  public IntegrationFlow vacancyFlow() {
    return IntegrationFlows.from("vacancyChannel")
        .split()
        .transform(Vacancy.class, Vacancy::getId)
        .handle("negotiationService", "getApplicantNegotiationsByVacancyId")
//        .handle((p, h) -> {throw new RuntimeException("test");}) // for error test
        .get();
  }

  @Bean
  public IntegrationFlow errorResponse() {
    return IntegrationFlows.from(errorChannel())
        .handle(message -> System.out.println("Data before error: " + ((MessagingException) message.getPayload()).getFailedMessage().getPayload()))
        .get();
  }

  public static void main(String[] args) {
    AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
    EmployerNegotiationGateway negotiationGateway = ctx.getBean(EmployerNegotiationGateway.class);
    EmployerVacancyGateway vacancyGateway = ctx.getBean(EmployerVacancyGateway.class);
    ResumeService resumeService = ctx.getBean(ResumeService.class);
    VacancyService vacancyService = ctx.getBean(VacancyService.class);
    NegotiationService negotiationService = ctx.getBean(NegotiationService.class);

    Resume resume1 = resumeService.save(new Resume("testF", "testL", "testD"));
    Resume resume2 = resumeService.save(new Resume("testF2", "testL2", "testD2"));
    Resume resume3 = resumeService.save(new Resume("testF3", "testL3", "testD3"));
    Vacancy vacancy = vacancyService.save(new Vacancy("vacancy description"));
    negotiationService.save(new Negotiation(resume1.getId(), vacancy.getId(), UserType.APPLICANT));
    negotiationService.save(new Negotiation(resume2.getId(), vacancy.getId(), UserType.EMPLOYER));
    negotiationService.save(new Negotiation(resume3.getId(), vacancy.getId(), UserType.APPLICANT));

    Collection<Negotiation> negotiations = vacancyGateway.process(vacancy);
    Collection<Resume> resumes = negotiationGateway.process(negotiations);
    System.out.println("Negotiations from applicants: ");
    resumes.stream().forEach(System.out::println);
  }
}
