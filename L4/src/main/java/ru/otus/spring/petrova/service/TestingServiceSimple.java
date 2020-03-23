package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.dao.QuestionDao;
import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.domain.UserInfo;

import java.util.List;

@Service
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
    UserInfo userInfo = questionService.introduce();
    List<Question> questions = questionDao.getQuestions(userInfo.getLocale());
    List<Integer> answer = questionService.askQuestions(questions);
    int rightAnswerCount = answerService.checkAnswers(answer, questions);
    questionService.returnResult(rightAnswerCount, questions.size());
  }
}
