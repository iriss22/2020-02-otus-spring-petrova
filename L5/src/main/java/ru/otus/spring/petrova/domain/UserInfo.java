package ru.otus.spring.petrova.domain;

public class UserInfo {
  private final String userName;

  public UserInfo(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

}
