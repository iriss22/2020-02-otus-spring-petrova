package ru.otus.spring.petrova.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.petrova.config.YamlProps;
import ru.otus.spring.petrova.domain.LoadQuestionError;
import ru.otus.spring.petrova.domain.Question;
import ru.otus.spring.petrova.service.TranslationService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class QuestionDaoFile implements QuestionDao {
  private final String pathToFile;
  private List<Question> savedQuestions;
  private Locale savedLocale;
  private final TranslationService translationService;

  public QuestionDaoFile(YamlProps yamlProps, TranslationService translationService) {
    pathToFile = yamlProps.getQuestionFileName();
    this.translationService = translationService;
  }

  @Override
  public List<Question> getQuestions() {
    if (savedQuestions != null && translationService.getLocale() == savedLocale) {
      return savedQuestions;
    }

    URL url = getClass().getClassLoader().getResource(String.format(pathToFile, translationService.getLocale().toString().toLowerCase()));
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()))) {
      List<Question> questions = bufferedReader
          .lines()
          .skip(1)
          .map(s -> {
            String[] values = s.split(";");
            return new Question(values[0], Integer.valueOf(values[5]), values[1], values[2], values[3], values[4]);
          })
          .collect(Collectors.toList());
      savedQuestions = questions;
      savedLocale = translationService.getLocale();
      return questions;
    } catch (Exception e) {
      throw new LoadQuestionError(e.getLocalizedMessage());
    }
  }
}
