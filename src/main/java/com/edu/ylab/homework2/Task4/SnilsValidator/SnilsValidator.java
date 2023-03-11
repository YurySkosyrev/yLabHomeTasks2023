package com.edu.ylab.homework2.Task4.SnilsValidator;

public interface SnilsValidator {
    /**
     * Проверяет, что в строке содержится валидный номер СНИЛС
     * @param snils снилс
     * @return результат проверки
     */
    boolean validate(String snils);

}
