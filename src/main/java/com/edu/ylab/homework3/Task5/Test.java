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



        File dataFile = new Generator().generate("data.txt", 50_000_000);
        System.out.println(new Validator(dataFile).isSorted()); // false

        long start = System.currentTimeMillis();
        File sortedFile = new Sorter().sortFile(dataFile);
        long finish = System.currentTimeMillis();
        System.out.println("Execution time: " + (finish-start));

        System.out.println(new Validator(sortedFile).isSorted()); // true

        System.out.println(new Validator(sortedFile).isHashEquals(sortedFile)); // true
    }
}
