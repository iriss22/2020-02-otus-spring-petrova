package ru.otus.spring.petrova.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDto {
  private String id;
  private BookDto book;
  private String text;

  public CommentDto(BookDto book, String text) {
    this.book = book;
    this.text = text;
  }
}
