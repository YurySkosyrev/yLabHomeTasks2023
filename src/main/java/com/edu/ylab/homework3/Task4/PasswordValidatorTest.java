package com.edu.ylab.homework3.Task4;

import com.edu.ylab.homework3.Task4.Exceptions.WrongLoginException;

/**
 Задание 4
 PasswordValidator
 Создать статический метод, который принимает на вход три параметра: login,
 password и confirmPassword и выбрасывает различные исключения.
 */

public class PasswordValidatorTest {
    public static void main(String[] args) throws WrongLoginException {
        System.out.println(PasswordValidator.validate("123%", "123*", "123*"));
    }
}
