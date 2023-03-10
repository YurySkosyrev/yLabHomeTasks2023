package com.edu.ylab.homework2.Task2;

public class ComplexNumber {
    private double a;
    private double b;

    public ComplexNumber(double a) {
        this.a = a;
    }

    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public static ComplexNumber sum(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.a + y.a, x.b + y.b);
    }


    public static ComplexNumber sub(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.a - y.a, x.b - y.b);
    }

    public static ComplexNumber mult(ComplexNumber x, ComplexNumber y){
        return new ComplexNumber(x.a*y.a - x.b*y.b, x.a*y.b+ x.b* y.a);
    }

    public double mod(){
        return Math.sqrt(this.a*this.a + this.b*this.b);
    }

    @Override
    public String toString() {
        if (this.b > 0) {
            return String.format("%.2f+%.2fi", this.a, this.b);
        }
        else {
            return String.format("%.2f%.2fi", this.a, this.b);
        }
    }
}
