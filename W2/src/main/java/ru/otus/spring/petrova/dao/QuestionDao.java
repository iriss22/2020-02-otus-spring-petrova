package ru.otus.spring.petrova.dao;

import ru.otus.spring.petrova.domain.Question;

import java.util.List;
import java.util.Locale;

public interface QuestionDao {
  List<Question> getQuestions(Locale locale);
}
