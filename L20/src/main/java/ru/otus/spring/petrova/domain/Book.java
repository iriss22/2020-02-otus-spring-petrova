package ru.otus.spring.petrova.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class Book {
  @Id
  private String id;

  @Field("name")
  private String name;

  @Field("author_id")
  private String authorId;

  @Field("genre_id")
  private String genreId;

  public Book(String name, String authorId, String genreId) {
    this.name = name;
    this.authorId = authorId;
    this.genreId = genreId;
  }
}
