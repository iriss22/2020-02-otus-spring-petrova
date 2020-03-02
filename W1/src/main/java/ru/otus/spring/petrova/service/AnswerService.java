package ru.otus.spring.petrova.service;

import ru.otus.spring.petrova.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

public class AnswerService {

  public int checkAnswers(List<Integer> userAnswers, List<Question> questions) {
    long rightAnswerCount =  IntStream.range(0, userAnswers.size())
        .filter(num -> userAnswers.get(num).equals(questions.get(num).getRightAnswer()))
        .count();
    return (int)rightAnswerCount;
  }
}
