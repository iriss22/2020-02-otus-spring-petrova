package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.h2.AuthorH2;
import ru.otus.spring.petrova.domain.h2.BookH2;
import ru.otus.spring.petrova.domain.mongo.AuthorM;
import ru.otus.spring.petrova.domain.mongo.BookM;

import java.util.stream.Collectors;

@Service
public class BookService {

  public BookM map(BookH2 h2) {
    return new BookM(
        String.valueOf(h2.getId()),
        h2.getName(),
        String.valueOf(h2.getAuthorH2().getId()),
        String.valueOf(h2.getGenreH2().getId()),
        h2.getCommentH2s().stream().map(comment -> comment.getText()).collect(Collectors.toList())
    );
  }
}
