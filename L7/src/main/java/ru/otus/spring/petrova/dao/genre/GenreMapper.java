package ru.otus.spring.petrova.dao.genre;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class GenreMapper implements RowMapper<Genre> {
  @Override
  public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
    return new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
  }
}
