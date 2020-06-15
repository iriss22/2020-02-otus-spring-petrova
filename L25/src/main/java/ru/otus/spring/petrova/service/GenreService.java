package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.h2.GenreH2;
import ru.otus.spring.petrova.domain.mongo.GenreM;

@Service
public class GenreService {

  public GenreM map(GenreH2 h2) {
    return new GenreM(String.valueOf(h2.getId()), h2.getName());
  }
}
