package ru.otus.spring.petrova.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "author")
public class AuthorM {
  @Id
  private String id;

  @Field("name")
  private String name;

  public AuthorM(String name) {
    this.name = name;
  }
}
