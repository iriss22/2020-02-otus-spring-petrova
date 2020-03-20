package ru.otus.spring.petrova.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.InputOutputCode;

import java.util.Locale;

@Service
public class TranslationService {

  private MessageSource messageSource;
  private Locale locale;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
    this.locale = Locale.getDefault();
  }

  public void changeLocale(Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getTranslation(InputOutputCode key, String... args) {
    return messageSource.getMessage(key.getCode(), args, locale);
  }
}
