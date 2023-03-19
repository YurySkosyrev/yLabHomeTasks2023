package com.edu.ylab.homework3.Task5;

import java.io.*;
import java.util.*;

public class Sorter {

    public File sortFile(File dataFile) throws IOException {


        int maxLine = 10;

        String tfile = "temp-file-";
        int filesCount = 0;
        int lines = 0;

        try (FileInputStream fileInputStream = new FileInputStream(dataFile);
             Scanner scanner = new Scanner(fileInputStream)) {
            List<Long> chunkList = new ArrayList<>();

            while (scanner.hasNextLong()) {
                chunkList.add(Long.parseLong(scanner.next()));
                lines++;
                if (lines == maxLine) {

                    Collections.sort(chunkList);

                    File file = new File(tfile + filesCount);
                    try (PrintWriter printWriter = new PrintWriter(file)) {
                        chunkList.stream().forEach(printWriter::println);
                        printWriter.flush();
                    }

                    filesCount++;
                    lines = 0;
                    chunkList.clear();
                }
            }
        }

        return new File("external-sorted.txt");
    }
}
