package ru.otus.spring.petrova.service;

import ru.otus.spring.petrova.dao.QuestionDao;
import ru.otus.spring.petrova.domain.Question;

import java.util.List;

public class TestingServiceSimple implements TestingService {
  private final QuestionService questionService;
  private final QuestionDao questionDao;
  private final AnswerService answerService;

  public TestingServiceSimple(QuestionService questionService, QuestionDao questionDao, AnswerService answerService) {
    this.questionService = questionService;
    this.questionDao = questionDao;
    this.answerService = answerService;
  }

  @Override
  public void testing() {
    List<Question> questions = questionDao.getQuestions();
    questionService.introduce();
    List<Integer> answer = questionService.askQuestions(questions);
    int rightAnswerCount = answerService.checkAnswers(answer, questions);
    questionService.returnResult(rightAnswerCount, questions.size());
  }
}
