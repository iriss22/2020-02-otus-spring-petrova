package ru.otus.spring.petrova.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.petrova.domain.InputOutputCode;

import java.util.Locale;

@Service
public class TranslationService {

  private MessageSource messageSource;

  public TranslationService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String getTranslation(InputOutputCode key, Locale locale, String... args) {
    return messageSource.getMessage(key.getCode(), args, locale);
  }
}
