package ru.otus.spring.petrova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class AuthorDto {
  private String id;
  private String name;

  public AuthorDto(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorDto authorDto = (AuthorDto) o;
    return Objects.equals(name, authorDto.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
