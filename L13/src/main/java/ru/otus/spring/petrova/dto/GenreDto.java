package ru.otus.spring.petrova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class GenreDto {
  private String id;
  private String name;

  public GenreDto(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GenreDto genreDto = (GenreDto) o;
    return Objects.equals(name, genreDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
