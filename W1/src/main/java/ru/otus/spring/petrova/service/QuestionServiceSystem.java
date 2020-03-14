package ru.otus.spring.petrova.service;

import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.domain.UserInfo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class QuestionServiceSystem implements QuestionService {
  private final Scanner sc;

  public QuestionServiceSystem() {
    sc = new Scanner(System.in);
  }

  @Override
  public UserInfo introduce() {
    UserInfo userInfo = new UserInfo();

    System.out.println("What is your first name?");
    userInfo.setFirstName(sc.next());

    System.out.println("What is your last name?");
    userInfo.setLastName(sc.next());
    return userInfo;
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
    System.out.println("your answer: ");
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
    System.out.println(String.format("Wrong answer! Select answer between 1 - %s", answersCount));
  }

  @Override
  public void returnResult(int rightCount, int questionCount) {
    System.out.print(String.format("your result: %s/%s", rightCount, questionCount));
  }
}
