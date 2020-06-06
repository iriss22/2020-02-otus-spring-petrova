package ru.otus.spring.petrova.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "author")
public class Author {
  @Id
  private String id;

  @Field("name")
  private String name;

  public Author(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Author author = (Author) o;
    return Objects.equals(name, author.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
