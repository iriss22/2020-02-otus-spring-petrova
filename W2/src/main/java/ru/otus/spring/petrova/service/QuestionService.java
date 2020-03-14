package ru.otus.spring.petrova.service;

import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.domain.UserInfo;

import java.util.List;

public interface QuestionService {
  UserInfo introduce();
  List<Integer> askQuestions(List<Question> questions);
  void returnResult(int rightCount, int questionCount);
}
