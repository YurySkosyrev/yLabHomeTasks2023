package com.edu.ylab.homework2.Task4;

import com.edu.ylab.homework2.Task4.SnilsValidator.impl.SnilsValidatorImpl;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
        System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
    }
}
