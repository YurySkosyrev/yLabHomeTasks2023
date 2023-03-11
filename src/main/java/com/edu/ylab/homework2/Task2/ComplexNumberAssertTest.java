package com.edu.ylab.homework2.Task2;

public class ComplexNumberAssertTest {
    public static void main(String[] args) {

        ComplexNumber x = new ComplexNumber(1, 2);
        ComplexNumber y = new ComplexNumber(2, -4);

        ComplexNumber sum = new ComplexNumber(3, -2);
        ComplexNumber sub = new ComplexNumber(-1, -6);

        assert sum.equals()

        System.out.println("x= " + x);
        System.out.println("y= " + y);
        System.out.println("x + y = " + ComplexNumber.sum(x,y));
        System.out.println("x - y = " + ComplexNumber.sub(x,y));
        System.out.println("x * y = " + ComplexNumber.mult(x,y));
        System.out.printf("Mod(x) = %.3f", x.mod());
    }
}
