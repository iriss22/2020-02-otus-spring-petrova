package ru.otus.spring.petrova.domain.h2;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name="comment")
public class CommentH2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(targetEntity = BookH2.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private BookH2 bookH2;

  @Column(name = "text")
  private String text;

  public CommentH2(BookH2 bookH2, String text) {
    this.bookH2 = bookH2;
    this.text = text;
  }
}
