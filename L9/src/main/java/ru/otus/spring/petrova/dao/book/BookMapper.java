package ru.otus.spring.petrova.dao.book;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.petrova.domain.Author;
import ru.otus.spring.petrova.domain.Book;
import ru.otus.spring.petrova.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
  @Override
  public Book mapRow(ResultSet resultSet, int i) throws SQLException {
    Author author = new Author(resultSet.getLong(3), resultSet.getString(4));
    Genre genre = new Genre(resultSet.getLong(5), resultSet.getString(6));
    return new Book(resultSet.getLong(1), resultSet.getString(2), author, genre);
  }
}
