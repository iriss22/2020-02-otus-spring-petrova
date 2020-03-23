package ru.otus.spring.petrova.domain;

import java.util.Arrays;
import java.util.List;

public class Question {
  private final String text;
  private final List<String> answers;
  private final int rightAnswer;

  public Question(String text, int rightAnswer, String... answer) {
    this.text = text;
    this.rightAnswer = rightAnswer;
    answers = Arrays.asList(answer);
  }

  public String getText() {
    return text;
  }

  public List<String> getAnswers() {
    return answers;
  }

  public int getRightAnswer() {
    return rightAnswer;
  }
}
