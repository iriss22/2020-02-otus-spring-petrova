package ru.otus.spring.petrova.domain;

import java.util.Locale;

public class UserInfo {
  private final String firstName;
  private final String lastName;
  private final Locale locale;

  public UserInfo(String firstName, String lastName, Locale locale) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.locale = locale;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Locale getLocale() {
    return locale;
  }
}
