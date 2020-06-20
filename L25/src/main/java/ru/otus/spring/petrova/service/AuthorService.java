package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.h2.AuthorH2;
import ru.otus.spring.petrova.domain.mongo.AuthorM;

@Service
public class AuthorService {

  public AuthorM map(AuthorH2 h2) {
    return new AuthorM(String.valueOf(h2.getId()), h2.getName());
  }
}
