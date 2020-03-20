package ru.otus.spring.petrova.domain;

public enum InputOutputCode {
  SELECT_LOCALE("result.selected_locale"),
  BAD_LOCALE_VALUE("result.bad_locale_value"),
  WRONG_ANSWER("result.wrong_answer"),
  TEST_RESULT("result.test_result"),
  TEST_RESULT_PASS("result.test_pass"),
  TEST_RESULT_FAILED("result.test_failed"),
  TEXT_HELLO("text.hello"),
  TEXT_BAD_USER_NAME("error.user_name"),
  TEXT_ENTER_USER_NAME("text.enter_user_name"),
  TEXT_FIRST_ANSWER("text.first_answer");

  private String code;

  InputOutputCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
