package com.edu.ylab.homework1;

/**
 * Домашнее задание №1. Задача №3.
 * MultTable.
 * На вход ничего не подаётся, необходимо распечатать таблицу
 * умножения чисел от 1 до 9 (включая)
 */

public class Task3 {
    public static void main(String[] args) {
        String multi = "";
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                multi = String.format("%dx%d=%d", j,  i, i*j);
                System.out.printf("%-8s", multi);
            }
            System.out.println();
        }
    }
}
