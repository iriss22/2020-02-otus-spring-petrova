package ru.otus.spring.petrova.domain;

import java.util.Locale;

public enum UserLocale {
  RU(new Locale("ru_RU")),
  EN(Locale.ENGLISH);

  Locale locale;

  UserLocale(Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

}
