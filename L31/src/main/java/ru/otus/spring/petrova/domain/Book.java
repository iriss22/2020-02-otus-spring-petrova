package ru.otus.spring.petrova.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
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
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id")
  private Author author;

  @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "genre_id")
  private Genre genre;

  @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true,
      mappedBy = "book")
  private List<Comment> comments;

  public Book(Long id) {
    this.id = id;
  }

  public Book(String name, Author author, Genre genre) {
    this.name = name;
    this.author = author;
    this.genre = genre;
  }

  public Book(String name, Author author, Genre genre, List<Comment> comments) {
    this.name = name;
    this.author = author;
    this.genre = genre;
    this.comments = comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return id == book.id &&
        name.equals(book.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, author, genre, comments);
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
