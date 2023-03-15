package com.edu.ylab.homework2.Task4.SnilsValidator.impl;

import com.edu.ylab.homework2.Task4.SnilsValidator.SnilsValidator;

public class SnilsValidatorImpl implements SnilsValidator {

    @Override
    public boolean validate(String snils) {

        if (snils == null || snils.length() != 11) {
            return false;
        }

        int sum = 0;
        int n = 9;

        for (int i = 0; i < 9; i++){
            char c = snils.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            else {
                int x = Character.digit(c,10);
                sum += x*n;
                n--;
            }
        }

        int lastTwoDigitsOfANumber = Character.digit(snils.charAt(9), 10)*10
                + Character.digit(snils.charAt(10), 10);

        int controlNumber = 0;

        if (sum < 100) {
            controlNumber = sum;
        }
        else if (sum == 0) {
            controlNumber = 0;
        }
        else {
            sum = sum%101;
            if (sum == 100) {
                controlNumber = 0;
            }
            else {
                controlNumber = sum;
            }
        }

        return lastTwoDigitsOfANumber == controlNumber;
    }
}
