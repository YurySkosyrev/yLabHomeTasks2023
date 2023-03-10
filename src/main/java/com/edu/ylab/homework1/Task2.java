package com.edu.ylab.homework1;

import java.util.Scanner;

/**
 * Домашнее задание №1. Задача №2.
 * Числа Пелли.
 * Вводится число n, выводится n-е число Пелли
 */

public class Task2 {
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(PellNumber(n));

            assert 0 == PellNumber(0) : "Test 1 wrong answer";
            assert 1 == PellNumber(1) : "Test 2 wrong answer";
            assert 29 ==PellNumber(5) : "Test 3 wrong answer";
        }
    }

    static int PellNumber(int n) {
        int p1 = 0;
        int p2 = 1;
        int pn = -1;

        if (n == 0) {
            return p1;
        } else if (n == 1) {
            return p2;
        } else {
            for (int i = 1; i < n; i++) {
                pn = 2 * p2 + p1;
                p1 = p2;
                p2 = pn;
            }
            return pn;
        }
    }
}
