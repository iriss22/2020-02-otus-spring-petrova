package ru.otus.spring.petrova.dao.author;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.petrova.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {
  @Override
  public Author mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Author(resultSet.getLong("id"), resultSet.getString("name"));
  }
}
