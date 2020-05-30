package ru.otus.spring.petrova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class AuthorDto {
  @EqualsAndHashCode.Exclude
  private Long id;
  private String name;

  public AuthorDto(String name) {
    this.name = name;
  }
}
