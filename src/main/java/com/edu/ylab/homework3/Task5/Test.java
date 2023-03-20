package com.edu.ylab.homework3.Task5;

import java.io.File;
import java.io.IOException;

/**
 Задание 5
 Задача - реализовать метод Sorter.sortFile используя алгоритм внешней
 сортировки слиянием
 */

public class Test {
    public static void main(String[] args) throws IOException {

        long start = System.currentTimeMillis();

        File dataFile = new Generator().generate("data.txt", 100_000_00);
        System.out.println(new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println(new Validator(sortedFile).isSorted()); // true

        long finish = System.currentTimeMillis();
        System.out.println(finish-start);

        System.out.println(new Validator(sortedFile).isHashEquals(sortedFile)); // true
    }
}
