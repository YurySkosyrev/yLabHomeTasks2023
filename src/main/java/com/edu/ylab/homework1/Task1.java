package com.edu.ylab.homework1;

import java.util.Scanner;

/**
 * Домашнее задание №1. Задача №1.
 * Stars.
 * Программе передается 3 параметра: количество строк, количество столбцов,
 * произвольный символ. Необходимо вывести фигуру, состоящую из заданного списка
 * строк и заданного количества столбцов и каждый элемент в которой равен
 * указанному символу
 */

public class Task1 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    System.out.print(template + " ");
                System.out.println();
            }
        }
    }
}
