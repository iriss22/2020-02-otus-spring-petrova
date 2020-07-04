package ru.otus.spring.petrova.domain;

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
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(targetEntity = Book.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private Book book;

  @Column(name = "text")
  private String text;

  public Comment(Book book, String text) {
    this.book = book;
    this.text = text;
  }
}
