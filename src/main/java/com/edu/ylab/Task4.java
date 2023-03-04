package com.edu.ylab;

import java.util.Random;
import java.util.Scanner;

/**
 * Домашнее задание №1. Задача №4.
 * Guess.
 * Пользователь угадывает число за 10 попыток
 */

public class Task4 {
    public static void main(String[] args) {
       int number = new Random().nextInt(100);
       int maxAttempts = 10;

       guess(number, maxAttempts);
    }

    static void guess(int number, int maxAttempts) {

        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток отгадать");


        try (Scanner scanner = new Scanner(System.in)) {
            while (maxAttempts > 0) {
                System.out.println("Введите число:");
                if (scanner.hasNextInt()) {

                    int userNumber = scanner.nextInt();

                    if (userNumber == number) {
                        System.out.println("Вы угадали с " + (11 - maxAttempts) + " попытки");
                        return;
                    } else if (userNumber < number) {
                        System.out.print("Моё число больше! ");
                    } else {
                        System.out.print("Моё число меньше! ");
                    }
                }
                else {
                    System.out.print("Введено не число. ");
                    scanner.next();
                }
                System.out.println("У вас осталось " + (maxAttempts - 1) + " попыток");
                maxAttempts--;
            }
        }
        System.out.println("Ты не угадал");
    }
}
