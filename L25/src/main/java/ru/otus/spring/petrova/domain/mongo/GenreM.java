package ru.otus.spring.petrova.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genre")
public class GenreM {
  @Id
  private String id;

  @Field("name")
  private String name;

  public GenreM(String name) {
    this.name = name;
  }
}
