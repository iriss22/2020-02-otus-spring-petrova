package ru.otus.spring.petrova.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Book {
  private long id;
  private String name;
  private Author author;
  private Genre genre;
}
