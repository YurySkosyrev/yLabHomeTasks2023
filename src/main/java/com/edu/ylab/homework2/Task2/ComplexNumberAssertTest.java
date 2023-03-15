package com.edu.ylab.homework2.Task2;

/**
 Test of ComplexNumber.class
 */
public class ComplexNumberAssertTest {
    public static void main(String[] args) {

        ComplexNumber x = new ComplexNumber(1, 2);
        ComplexNumber y = new ComplexNumber(2, -4);

        ComplexNumber sum = new ComplexNumber(3, -2);
        ComplexNumber sub = new ComplexNumber(-1, 6);
        ComplexNumber mult = new ComplexNumber(10);

        assert sum.equals(x.sum(y)) : "Sum test failed";
        assert sub.equals(x.sub(y)) : "Sub test failed";
        assert mult.equals(x.mult(y)) : "Mult test failed";
        assert (Math.abs(2.236 - x.mod()) < 0.001) : "Mod test failed";
    }
}
