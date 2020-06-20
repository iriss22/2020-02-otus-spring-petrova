package ru.otus.spring.petrova.domain.h2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookH2 {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(targetEntity = AuthorH2.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private AuthorH2 authorH2;

  @ManyToOne(targetEntity = GenreH2.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id")
  private GenreH2 genreH2;

  @OneToMany(targetEntity = CommentH2.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id")
  private List<CommentH2> commentH2s;

  public BookH2(Long id) {
    this.id = id;
  }

  public BookH2(String name, AuthorH2 authorH2, GenreH2 genreH2) {
    this.name = name;
    this.authorH2 = authorH2;
    this.genreH2 = genreH2;
  }

  public BookH2(String name, AuthorH2 authorH2, GenreH2 genreH2, List<CommentH2> commentH2s) {
    this.name = name;
    this.authorH2 = authorH2;
    this.genreH2 = genreH2;
    this.commentH2s = commentH2s;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    BookH2 bookH2 = (BookH2) o;
    return id == bookH2.id &&
        name.equals(bookH2.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, authorH2, genreH2, commentH2s);
  }

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", author=" + authorH2.getName() +
        ", genre=" + genreH2.getName() +
        '}';
  }
}
