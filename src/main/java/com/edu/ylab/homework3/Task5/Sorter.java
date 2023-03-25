package com.edu.ylab.homework3.Task5;

import java.io.*;
import java.util.*;

public class Sorter {

    public File sortFile(File dataFile) throws IOException {


//        long memory = 1024;
        long memory = Runtime.getRuntime().freeMemory();

        long readBytesSize = 0L;

        String DIR_PATH = "test";
        File dir = new File(DIR_PATH);
        dir.mkdir();

        String tempFilePrefix = DIR_PATH + "//temp-file-";
        int filesCount = 0;

        //Проходим по исходному файлу и создаём отсортированные файлы
        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            List<Long> currentList = new ArrayList<>();

            while (scanner.hasNextLong()) {
                String nextString = scanner.next();
                readBytesSize += nextString.getBytes().length;
                currentList.add(Long.parseLong(nextString));
                if (readBytesSize > memory) {

                    Collections.sort(currentList);

                    File file = new File(tempFilePrefix + filesCount);
                    try (PrintWriter printWriter = new PrintWriter(file)) {
                        currentList.stream().forEach(printWriter::println);
                        printWriter.flush();
                    }

                    filesCount++;
                    System.out.println(filesCount);
                    readBytesSize = 0;
                    currentList.clear();
                }
            }
            if (!currentList.isEmpty()) {
                Collections.sort(currentList);

                File file = new File(tempFilePrefix + filesCount);
                try (PrintWriter printWriter = new PrintWriter(file)) {
                    currentList.stream().forEach(printWriter::println);
                    printWriter.flush();
                }

                filesCount++;
                System.out.println(filesCount);
            }
        }

        // Сливаем файлы в один
        Scanner[] scanners = new Scanner[filesCount];
        Map<Integer, Long> minValues = new HashMap<>();

        PrintWriter printWriter = new PrintWriter("external-sorted.txt");
        try {
            // Создаём Map - ключ номер файла, значение - очередное значение Long
            for (int i = 0; i < filesCount; i++) {
                FileInputStream fileInputStream = new FileInputStream(tempFilePrefix + i);
                Scanner scanner = new Scanner(fileInputStream);
                scanners[i] = scanner;

                if (scanner.hasNextLong()) {
                    minValues.put(i, Long.parseLong(scanner.next()));
                }
            }

            // Записываем очередное минимальное значение из всех файлов, обновляем Map

            while (!minValues.isEmpty()) {
                Integer fileNumber = 0;
                Long minValue = Long.MAX_VALUE;
                for (Map.Entry<Integer, Long> entry : minValues.entrySet()) {
                    if (entry.getValue() <= minValue) {
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

        } finally {
            for (int i = 0; i < filesCount; i++) {
                scanners[i].close();
            }
            printWriter.close();

            // Удаляем временные файлы
            for (int i = 0; i < filesCount; i++) {
                File file = new File(tempFilePrefix + i);
                file.delete();
            }
            dir.delete();
        }

        return new File("external-sorted.txt");
    }
}
