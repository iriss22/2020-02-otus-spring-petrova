package ru.otus.spring.petrova.dto;

public class Language {
  private String id;
  private String name;

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Language{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
