package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Validator {
    private File file;
    public Validator(File file) {
        this.file = file;
    }
    public boolean isSorted() {
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            long prev = Long.MAX_VALUE;
            while (scanner.hasNextLong()) {
                long current = scanner.nextLong();
                if (current > prev) {
                    return false;
                } else {
                    prev = current;
                }
            }
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean isHashEquals(File sortFile) {
        Long hash1 = 0L;
        Long hash2 = 0L;
        try (Scanner scanner1 = new Scanner(new FileInputStream(file));
                Scanner scanner2 = new Scanner(new FileInputStream(sortFile)) ){
            while (scanner1.hasNextLong()) {
                hash1 += scanner1.next().hashCode();
            }
            while (scanner2.hasNextLong()) {
                hash2 += scanner2.next().hashCode();
            }
            } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }


        return hash1.equals(hash2);
    }
}