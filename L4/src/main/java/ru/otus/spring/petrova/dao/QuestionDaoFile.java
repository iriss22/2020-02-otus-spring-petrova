package ru.otus.spring.petrova.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.config.YamlProps;
import ru.otus.spring.petrova.domain.LoadQuestionError;
import ru.otus.spring.petrova.domain.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoFile implements QuestionDao {
  private final String pathToFile;

  public QuestionDaoFile(YamlProps yamlProps) {
    pathToFile = yamlProps.getQuestionFileName();
  }

  @Override
  public List<Question> getQuestions(Locale locale) {
    URL url = getClass().getClassLoader().getResource(String.format(pathToFile, locale.toString().toLowerCase()));

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()))) {
      List<Question> questions = bufferedReader
          .lines()
          .skip(1)
          .map(s -> {
            String[] values = s.split(";");
            return new Question(values[0], Integer.valueOf(values[5]), values[1], values[2], values[3], values[4]);
          })
          .collect(Collectors.toList());
      return questions;
    } catch (Exception e) {
      throw new LoadQuestionError(e.getLocalizedMessage());
    }
  }
}
