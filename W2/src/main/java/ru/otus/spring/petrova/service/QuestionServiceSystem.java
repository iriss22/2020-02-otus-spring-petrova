package ru.otus.spring.petrova.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.InputOutputCode;
import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.domain.UserInfo;
import ru.otus.spring.petrova.domain.UserLocale;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

@Service
public class QuestionServiceSystem implements QuestionService {
  private final Scanner sc;
  private Locale locale;

  private TranslationService translationService;

  @Value("${answers.right.count}")
  private int answersCountToPass;

  public QuestionServiceSystem(TranslationService translationService) {
    sc = new Scanner(System.in);
    this.translationService = translationService;
    locale = Locale.getDefault();
  }

  @Override
  public UserInfo introduce() {
    System.out.println(translationService.getTranslation(InputOutputCode.SELECT_LANGUAGE, locale));
    String enteredLocale = sc.nextLine();
    try {
      locale = UserLocale.valueOf(enteredLocale.toUpperCase()).getLocale();
      System.out.println(translationService.getTranslation(InputOutputCode.SELECT_LOCALE, locale, locale.toString()));
    } catch (IllegalArgumentException e) {
      System.out.println(translationService.getTranslation(InputOutputCode.BAD_LOCALE_VALUE, locale, enteredLocale, locale.toString()));
    }

    System.out.println(translationService.getTranslation(InputOutputCode.REQUEST_FIRST_NAME, locale));
    String firstName = sc.next();

    System.out.println(translationService.getTranslation(InputOutputCode.REQUEST_LAST_NAME, locale));
    String lastName = sc.next();

    return new UserInfo(firstName, lastName, locale);
  }

  @Override
  public List<Integer> askQuestions(List<Question> questions) {
    List<Integer> userAnswers = new ArrayList<>();

      for (Question question : questions) {
        System.out.println(question.getText());
        int answersCount = question.getAnswers().size();
        for (int i = 0; i < answersCount; i++) {
          System.out.println(String.format("%s. %s", i+1, question.getAnswers().get(i)));
        }
        userAnswers.add(getAnswer(answersCount));
      }

    return userAnswers;
  }

  private Integer getAnswer(int answersCount) {
    System.out.println(translationService.getTranslation(InputOutputCode.YOU_ANSWER, locale));
    Integer answer = -1;
    while (answer == -1) {
      try {
        answer = sc.nextInt();
        if (answer < 0 || answer > answersCount) {
          printWrongAnswer(answersCount);
          answer = -1;
        }
      } catch (InputMismatchException e) {
        printWrongAnswer(answersCount);
        sc.next();
      }
    }
    return answer;
  }

  private void printWrongAnswer(int answersCount) {
    System.out.println(translationService.getTranslation(InputOutputCode.WRONG_ANSWER, locale, String.valueOf(answersCount)));
  }

  @Override
  public void returnResult(int rightCount, int questionCount) {
    System.out.println(translationService.getTranslation(InputOutputCode.TEST_RESULT, locale, String.valueOf(rightCount),
        String.valueOf(questionCount)));
    if (rightCount >= answersCountToPass) {
      System.out.println(translationService.getTranslation(InputOutputCode.TEST_RESULT_PASS, locale));
    } else {
      System.out.println(translationService.getTranslation(InputOutputCode.TEST_RESULT_FAILED, locale));
    }
  }
}
