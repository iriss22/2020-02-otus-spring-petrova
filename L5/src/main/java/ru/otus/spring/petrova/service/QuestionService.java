package ru.otus.spring.petrova.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.dao.QuestionDao;
import ru.otus.spring.petrova.domain.Question;

@Service
@RequiredArgsConstructor
public class QuestionService {

  private final QuestionDao questionDao;

  public String getQuestionAndAnswers(int questionNumber) {
    Question question = questionDao.getQuestions().get(questionNumber);
    StringBuilder questionBuilder = new StringBuilder();

    questionBuilder.append(question.getText());
    questionBuilder.append(System.lineSeparator());
    int answersCount = question.getAnswers().size();
    for (int i = 0; i < answersCount; i++) {
      questionBuilder.append(String.format("%s. %s", i+1, question.getAnswers().get(i)));
      questionBuilder.append(System.lineSeparator());
    }

    return questionBuilder.toString();
  }

  public int getQuestionCount() {
    return questionDao.getQuestions().size();
  }
}
