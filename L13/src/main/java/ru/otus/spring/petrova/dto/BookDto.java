package ru.otus.spring.petrova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class BookDto {
  private String id;
  private String name;
  private AuthorDto author;
  private GenreDto genre;
  private List<CommentDto> comments;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookDto bookDto = (BookDto) o;
    return Objects.equals(name, bookDto.name) &&
        Objects.equals(author, bookDto.author) &&
        Objects.equals(genre, bookDto.genre) &&
        Objects.equals(comments, bookDto.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, author, genre, comments);
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author=" + author.getName() +
        ", genre=" + genre.getName() +
        '}';
  }
}
