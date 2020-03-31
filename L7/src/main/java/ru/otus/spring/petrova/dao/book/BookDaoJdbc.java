package ru.otus.spring.petrova.dao.book;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.DataNotFound;
import ru.otus.spring.petrova.dao.author.AuthorMapper;
import ru.otus.spring.petrova.dao.genre.GenreMapper;
import ru.otus.spring.petrova.domain.Book;

import java.util.Collections;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
  private final NamedParameterJdbcOperations jdbc;
  private final BookMapper bookMapper;

  @Override
  public long create(String name, long authorId, long genreId) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    MapSqlParameterSource parameters = new MapSqlParameterSource()
        .addValue("name", name)
        .addValue("author_id", authorId)
        .addValue("genre_id", genreId);
    jdbc.update("INSERT INTO book (name, author_id, genre_id) VALUES(:name, :author_id, :genre_id)",
        parameters, keyHolder, new String[] { "id" });
    return (long)keyHolder.getKey();
  }

  @Override
  public void delete(long id) throws DataNotFound {
    int cnt = jdbc.update("DELETE FROM book WHERE id = :id", Collections.singletonMap("id", id));
    if (cnt == 0) {
      throw new DataNotFound(id);
    }
  }

  @Override
  public void update(long id, String name) throws DataNotFound {
    int cnt = jdbc.update("UPDATE book SET name = :name WHERE id = :id", Map.of("id", id, "name", name));
    if (cnt == 0) {
      throw new DataNotFound(id);
    }
  }

  @Override
  public Book get(long id) throws DataNotFound {
    try {
    return jdbc.queryForObject("SELECT b.id book_id, b.name book_name, a.id author_id, a.name author_name, g.id genre_id, g.name genre_name " +
        "FROM book b " +
        " JOIN author a on b.author_id = a.id" +
        " JOIN genre g on b.genre_id = g.id" +
        " WHERE b.id = :id", Collections.singletonMap("id", id), bookMapper);
    } catch (EmptyResultDataAccessException e) {
      throw new DataNotFound(id, e);
    }
  }
}
