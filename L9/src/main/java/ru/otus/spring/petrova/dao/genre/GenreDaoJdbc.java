package ru.otus.spring.petrova.dao.genre;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.domain.Genre;

import java.util.Collections;

@Repository
public class GenreDaoJdbc implements GenreDao {
  private final NamedParameterJdbcOperations jdbc;

  public GenreDaoJdbc(NamedParameterJdbcOperations parameterJdbcOperations) {
    this.jdbc = parameterJdbcOperations;
  }

  @Override
  public long create(Genre genre) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", genre.getName());
    jdbc.update("INSERT INTO genre (name) VALUES(:name)", parameters, keyHolder, new String[] { "id" });
    return (long)keyHolder.getKey();
  }

  @Override
  public Genre get(long id) {
    return jdbc.queryForObject("SELECT * FROM genre WHERE id = :id", Collections.singletonMap("id", id), new GenreMapper());
  }
}
