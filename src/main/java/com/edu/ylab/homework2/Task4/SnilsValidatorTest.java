package com.edu.ylab.homework2.Task4;

import com.edu.ylab.homework2.Task4.SnilsValidator.impl.SnilsValidatorImpl;

/**
 Задача 4

 Реализовать интерфейс SnilsValidator
 Который возвращает true если номер СНИЛС валидный, false - в противном случае.
 Можно считать, что номер передается в виде строки, содержащей исключительно
 цифры от 0 до 9.
 */


public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true

        //Assert-тесты в классе SnilsValidatorImplTest
    }


}
