package com.edu.ylab.homework2.Task2;

import java.util.Objects;

public class ComplexNumber {
    private double a;
    private double b;

    public ComplexNumber(double a) {

        this.a = a;
        this.b = 0;
    }

    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public ComplexNumber sum(ComplexNumber y){
        return new ComplexNumber(this.a + y.a, this.b + y.b);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.a, a) == 0 && Double.compare(that.b, b) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
