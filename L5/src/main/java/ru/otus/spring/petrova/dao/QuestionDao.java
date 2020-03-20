package ru.otus.spring.petrova.dao;

import ru.otus.spring.petrova.domain.Question;

import java.util.List;

public interface QuestionDao {
  List<Question> getQuestions();
}
