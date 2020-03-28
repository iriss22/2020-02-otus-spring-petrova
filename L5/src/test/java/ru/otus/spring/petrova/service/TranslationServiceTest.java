package ru.otus.spring.petrova.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.petrova.domain.InputOutputCode;
import ru.otus.spring.petrova.domain.UserLocale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TranslationServiceTest {

  @Autowired
  private TranslationService translationService;

  private final static String NAME = "Ira";

  @Test
  public void testRussianTranslation() {
    translationService.changeLocale(UserLocale.RU.getLocale());
    assertEquals(String.format("Привет, %s!", NAME), translationService.getTranslation(InputOutputCode.TEXT_HELLO, NAME));
  }

  @Test
  public void testUSTranslation() {
    translationService.changeLocale(UserLocale.US.getLocale());
    assertEquals(String.format("Hello, %s!", NAME), translationService.getTranslation(InputOutputCode.TEXT_HELLO, NAME));
  }

  @Test
  public void testEnglishTranslation() {
    translationService.changeLocale(UserLocale.EN.getLocale());
    assertEquals(String.format("Hello, %s!", NAME), translationService.getTranslation(InputOutputCode.TEXT_HELLO, NAME));
  }
}
