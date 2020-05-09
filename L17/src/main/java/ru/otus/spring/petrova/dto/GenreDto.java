package ru.otus.spring.petrova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
public class GenreDto {
  @EqualsAndHashCode.Exclude
  private Long id;
  private String name;

  public GenreDto(String name) {
    this.name = name;
  }
}
