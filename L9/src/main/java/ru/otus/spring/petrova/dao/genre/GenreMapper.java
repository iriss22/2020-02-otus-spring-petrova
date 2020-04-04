package ru.otus.spring.petrova.dao.genre;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.petrova.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {
  @Override
  public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
  }
}
