package ru.otus.spring.petrova.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "negotiation")
public class Negotiation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "resume_id", nullable = false)
  private Long resumeId;

  @Column(name = "vacancy_id", nullable = false)
  private Long vacancyId;

  @Column(name = "author", nullable = false)
  private UserType author;

  public Negotiation(Long resumeId, Long vacancyId, UserType author) {
    this.resumeId = resumeId;
    this.vacancyId = vacancyId;
    this.author = author;
  }
}
