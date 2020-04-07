package ru.otus.spring.petrova.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.dao.author.AuthorMapper;
import ru.otus.spring.petrova.dao.genre.GenreMapper;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class BookMapper implements RowMapper<Book> {

  private final AuthorMapper authorMapper;
  private final GenreMapper genreMapper;

  @Override
  public Book mapRow(ResultSet resultSet, int i) throws SQLException {
    Author author = authorMapper.mapRow(resultSet, i);
    Genre genre = genreMapper.mapRow(resultSet, i);
    return new Book(resultSet.getLong("book_id"), resultSet.getString("book_name"), author, genre);
  }
}
