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


    public ComplexNumber sub(ComplexNumber y){
        return new ComplexNumber(this.a - y.a, this.b - y.b);
    }

    public ComplexNumber mult(ComplexNumber y){
        return new ComplexNumber(this.a*y.a - this.b*y.b, this.a*y.b+ this.b* y.a);
    }

    public double mod(){
        return Math.sqrt(this.a*this.a + this.b*this.b);
    }

    @Override
    public String toString() {
        if (this.b > 0) {
            return String.format("%.2f+%.2fi", this.a, this.b);
        }
        else if (this.b < 0){
            return String.format("%.2f%.2fi", this.a, this.b);
        }
        else {
            return String.format("%.2f", this.a);
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
