package ru.otus.spring.petrova.dao.author;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.domain.Author;

import java.util.Collections;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

  private final NamedParameterJdbcOperations jdbc;

  @Override
  public long create(Author author) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("name", author.getName());
    jdbc.update("INSERT INTO author (name) VALUES(:name)", parameters, keyHolder, new String[] { "id" });
    return (long)keyHolder.getKey();
  }

  @Override
  public Author get(long id) {
    return jdbc.queryForObject("SELECT a.id author_id, a.name author_name FROM author a WHERE id = :id",
        Collections.singletonMap("id", id), new AuthorMapper());
  }
}
