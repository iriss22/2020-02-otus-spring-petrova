package ru.otus.spring.petrova.domain;

public enum InputOutputCode {
  SELECT_LANGUAGE("text.select_language"),
  SELECT_LOCALE("result.selected_locale"),
  BAD_LOCALE_VALUE("result.bad_locale_value"),
  REQUEST_FIRST_NAME("questions.introduce.first_name"),
  REQUEST_LAST_NAME("questions.introduce.last_name"),
  YOU_ANSWER("text.your_answer"),
  WRONG_ANSWER("result.wrong_answer"),
  TEST_RESULT("result.test_result"),
  TEST_RESULT_PASS("result.test_pass"),
  TEST_RESULT_FAILED("result.test_failed");

  private String code;

  InputOutputCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
