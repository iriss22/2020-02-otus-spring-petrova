package ru.otus.spring.petrova.domain;

public class AnswerError extends Exception {
    private int maxCount;

    public AnswerError(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxCount() {
        return maxCount;
    }
}
