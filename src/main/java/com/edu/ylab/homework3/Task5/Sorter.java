package com.edu.ylab.homework3.Task5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Sorter {

    public File sortFile(File dataFile) throws IOException {

        int maxLine = 100000;

        String DIR_PATH = "C://Test";
        File dir = new File(DIR_PATH);
        dir.mkdir();

        String tempFilePrefix = DIR_PATH + "//temp-file-";
        int filesCount = 0;
        int currentLine = 0;

        //Проходим по исходному файлу и создаём отсортированные файлы
        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            List<Long> currentList = new ArrayList<>();

            while (scanner.hasNextLong()) {
                currentList.add(Long.parseLong(scanner.next()));
                currentLine++;
                if (currentLine == maxLine) {

                    Collections.sort(currentList);

                    File file = new File(tempFilePrefix + filesCount);
                    try (PrintWriter printWriter = new PrintWriter(file)) {
                        currentList.stream().forEach(printWriter::println);
                        printWriter.flush();
                    }

                    filesCount++;
                    currentLine = 0;
                    currentList.clear();
                }
            }
            if(currentLine > 0) {
                Collections.sort(currentList);

                File file = new File(tempFilePrefix + filesCount);
                try (PrintWriter printWriter = new PrintWriter(file)) {
                    currentList.stream().forEach(printWriter::println);
                    printWriter.flush();
                }
                filesCount++;
            }
        }

        // Сливаем файлы в один
        Scanner[] scanners = new Scanner[filesCount];
        Map<Integer, Long> minValues = new HashMap<>();

        // Создаём Map - ключ номер файла, значение - очередное значение Long
        for (int i = 0; i < filesCount; i++) {
            FileInputStream fileInputStream = new FileInputStream(tempFilePrefix + i);
            Scanner scanner = new Scanner(fileInputStream);
            scanners[i] = scanner;

            if (scanner.hasNextLong()) {
                minValues.put(i, Long.parseLong(scanner.next()));
            }
        }

        PrintWriter printWriter = new PrintWriter("external-sorted.txt");

        // Записываем очередное минимальное значение из всех файлов, обновляем Map
        while (!minValues.isEmpty()) {
            Long minValue = Long.MAX_VALUE;
            Integer fileNumber = 0;
            for (Map.Entry<Integer, Long> entry : minValues.entrySet()) {
                if (entry.getValue() < minValue) {
                    minValue = entry.getValue();
                    fileNumber = entry.getKey();
                }
            }

            printWriter.println(minValue);

            if (scanners[fileNumber].hasNextLong()) {
                minValues.put(fileNumber, Long.parseLong(scanners[fileNumber].next()));
            } else {
                minValues.remove(fileNumber);
            }
        }

        for (int i = 0; i < filesCount; i++) {
            scanners[i].close();
        }
        printWriter.close();


        // Удаляем временные файлы
        for (int i = 0; i < filesCount; i++) {
            File file = new File(tempFilePrefix + i);
            file.delete();
        }

        return new File("external-sorted.txt");
    }
}
