package com.edu.ylab.homework2.Task1.Sequences.impl;

import com.edu.ylab.homework2.Task1.Sequences.Sequences;

public class SequencesImpl implements Sequences {

    @Override
    public void a(int n) {

        System.out.print("A. ");

        for(int i = 1; i <= n; i++){
            System.out.print(2*i + " ");
        }
        System.out.println();
    }

    @Override
    public void b(int n) {

        System.out.print("B. ");

        for(int i = 0; i < n; i++){
            System.out.print(2*i+1 + " ");
        }
        System.out.println();
    }

    @Override
    public void c(int n) {

        System.out.print("C. ");

        for(int i = 1; i <= n; i++){
            System.out.print(i*i + " ");
        }
        System.out.println();
    }

    @Override
    public void d(int n) {

        System.out.print("D. ");

        for(int i = 1; i <= n; i++){
            System.out.print(i*i*i + " ");
        }
        System.out.println();
    }

    @Override
    public void e(int n) {

        System.out.print("E. ");

        int x0 = -1;
        int xN;
        for(int i = 1; i <= n; i++){
            xN = x0*(-1);
            x0 = xN;
            System.out.print(xN + " ");
        }
        System.out.println();
    }

    @Override
    public void f(int n) {

        System.out.print("F. ");

        int x0 = 1;
        for(int i = 1; i <= n; i++){
            System.out.print(x0*i + " ");
            x0 *= -1;
        }
        System.out.println();
    }

    @Override
    public void g(int n) {

        System.out.print("G. ");

        int x0 = 1;
        for(int i = 1; i <= n; i++){
            System.out.print(x0*i*i + " ");
            x0 *= -1;
        }
        System.out.println();
    }

    @Override
    public void h(int n) {
        System.out.print("I. ");

        int x0 = 1;
        for(int i = 1; i <= n; i++){
            if (i%2 == 0) {
                System.out.print("0 ");
            }
            else {
                System.out.print(x0 + " ");
                x0++;
            }
        }
        System.out.println();
    }

    @Override
    public void i(int n) {
        System.out.print("I. ");

        int x0 = 1;
        for(int i = 1; i <= n; i++){
            System.out.print(x0 + " ");
            x0 *= i + 1;
        }
        System.out.println();
    }

    @Override
    public void j(int n) {

        System.out.print("J. ");

        int x0 = 1;
        int x1 = 1;
        int xN = -1;

        if (n == 1) {
            System.out.println(x0);
        }
        else if (n == 2) {
            System.out.println(x0 + " " + x1);
        }

        else {
            System.out.print(x0 + " " + x1 + " ");
        }
        for(int i = 3; i <= n; i++){
            xN = x0 + x1;
            x0 = x1;
            x1 = xN;
            System.out.print(xN + " ");
        }
        System.out.println();
    }
}
