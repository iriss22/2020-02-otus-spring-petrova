package ru.otus.spring.petrova.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;
import ru.otus.spring.petrova.domain.AnswerError;
import ru.otus.spring.petrova.domain.InputOutputCode;
import ru.otus.spring.petrova.domain.UserInfo;
import ru.otus.spring.petrova.domain.UserLocale;
import ru.otus.spring.petrova.service.AnswerService;
import ru.otus.spring.petrova.service.QuestionService;
import ru.otus.spring.petrova.service.TranslationService;

import java.util.ArrayList;
import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class TestingStudentCommands {
    private UserInfo userInfo;

    private final TranslationService translationService;
    private final AnswerService answerService;
    private final QuestionService questionService;

    private int questionNumber;
    private List<Integer> userAnswers;
    private boolean answerAsked;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String userName) {
        if (StringUtils.isEmpty(userName)) {
            return translationService.getTranslation(InputOutputCode.TEXT_BAD_USER_NAME);
        }
        userInfo = new UserInfo(userName);
        prepareToTest();
        return translationService.getTranslation(InputOutputCode.TEXT_HELLO, userName);
    }

    @ShellMethod(value = "Locale command", key = {"ln", "locale"})
    public String locale(@ShellOption String enteredLocale) {
        try{
            translationService.changeLocale(UserLocale.valueOf(enteredLocale.toUpperCase()).getLocale());
        } catch (IllegalArgumentException e) {
            System.out.println(translationService.getTranslation(InputOutputCode.BAD_LOCALE_VALUE, enteredLocale));
        }
        return translationService.getTranslation(InputOutputCode.SELECT_LOCALE, translationService.getLocale().toString());
    }

    @ShellMethod(value = "Start test command", key = {"s", "start"})
    @ShellMethodAvailability(value = "isUserLogined")
    public String start() {
        answerAsked = true;
        return questionService.getQuestionAndAnswers(questionNumber);
    }

    @ShellMethod(value = "Send answer and get next question or result command", key = {"a", "answer"})
    @ShellMethodAvailability(value = "isAnswerAsked")
    public String sendAnswer(@ShellOption() int answer) {
        try {
            answerService.validate(answer, questionNumber);
        } catch (AnswerError answerError) {
            return translationService.getTranslation(InputOutputCode.WRONG_ANSWER, String.valueOf(answerError.getMaxCount()));
        }

        userAnswers.add(answer);

        if (questionService.getQuestionCount() == ++questionNumber) {
            prepareToTest();
            return printResult();
        }
        return questionService.getQuestionAndAnswers(questionNumber);
    }

    private void prepareToTest() {
        questionNumber = 0;
        answerAsked = false;
        userAnswers = new ArrayList<>();
    }

    private String printResult() {
        int countRight = answerService.checkAnswers(userAnswers);
        System.out.println(translationService.getTranslation(InputOutputCode.TEST_RESULT, String.valueOf(countRight),
                String.valueOf(questionService.getQuestionCount())));
        if (answerService.isTestPassed(countRight)) {
            return translationService.getTranslation(InputOutputCode.TEST_RESULT_PASS);
        } else {
            return translationService.getTranslation(InputOutputCode.TEST_RESULT_FAILED);
        }
    }

    private Availability isUserLogined() {
        return userInfo == null? Availability.unavailable(translationService.getTranslation(InputOutputCode.TEXT_ENTER_USER_NAME)) :
                Availability.available();
    }

    private Availability isAnswerAsked() {
        return answerAsked ? Availability.available() : Availability.unavailable(translationService.getTranslation(InputOutputCode.TEXT_FIRST_ANSWER));
    }
}
