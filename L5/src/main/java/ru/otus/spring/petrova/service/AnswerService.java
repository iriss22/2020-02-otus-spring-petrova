package ru.otus.spring.petrova.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.config.YamlProps;
import ru.otus.spring.petrova.dao.QuestionDao;
import ru.otus.spring.petrova.domain.AnswerError;
import ru.otus.spring.petrova.domain.Question;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class AnswerService {

  private final int answersCountToPass;
  private final QuestionDao questionDao;

  public AnswerService(YamlProps props, QuestionDao questionDao) {
    answersCountToPass = props.getRightAnswersCount();
    this.questionDao = questionDao;
  }

  public int checkAnswers(List<Integer> userAnswers) {
    List<Question> questions = questionDao.getQuestions();
    long rightAnswerCount =  IntStream.range(0, userAnswers.size())
        .filter(num -> userAnswers.get(num).equals(questions.get(num).getRightAnswer()))
        .count();
    return (int)rightAnswerCount;
  }

  public boolean isTestPassed(int countRight) {
    if (countRight >= answersCountToPass) {
      return true;
    }
    return false;
  }

  public void validate(int answer, int questionNumber) throws AnswerError {
    Question question = questionDao.getQuestions().get(questionNumber);
    int maxCount = question.getAnswers().size();
    if (answer <= 0 || answer > maxCount) {
      throw new AnswerError(maxCount);
    }
  }
}
