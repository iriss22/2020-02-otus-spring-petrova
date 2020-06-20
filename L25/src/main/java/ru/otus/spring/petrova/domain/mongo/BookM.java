package ru.otus.spring.petrova.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book")
public class BookM {
  @Id
  private String id;

  @Field("name")
  private String name;

  @Field("author_id")
  private String authorId;

  @Field("genre_id")
  private String genreId;

  @Field("comments")
  private List<String> comments;

  public BookM(String name, String authorId, String genreId) {
    this.name = name;
    this.authorId = authorId;
    this.genreId = genreId;
    comments = new ArrayList<>();
  }

  public void addComment(String comment) {
    comments.add(comment);
  }
}
