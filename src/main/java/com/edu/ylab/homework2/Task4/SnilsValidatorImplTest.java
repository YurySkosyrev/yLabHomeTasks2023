package com.edu.ylab.homework2.Task4;

import com.edu.ylab.homework2.Task4.SnilsValidator.SnilsValidator;
import com.edu.ylab.homework2.Task4.SnilsValidator.impl.SnilsValidatorImpl;

/** Test of SnilsValidatorImpl.class
 */

public class SnilsValidatorImplTest {
    public static void main(String[] args) {
        SnilsValidator validator = new SnilsValidatorImpl();

        assert !validator.validate("01468870570") : "Test 1 wrong answer";
        assert validator.validate("90114404441") : "Test 2 wrong answer";
        assert !validator.validate(null) : "Test 3 wrong answer";
        assert !validator.validate("") : "Test 4 wrong answer";
        assert !validator.validate("90114404441a") : "Test 5 wrong answer";
        assert !validator.validate("9011440444") : "Test 6 wrong answer";

    }
}
