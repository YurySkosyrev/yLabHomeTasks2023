package com.edu.ylab.homework3.Task5;

import java.io.*;
import java.util.Arrays;

public class Sorter1 {

    public File sortFile(File dataFile) throws IOException {


        int fileSize = (int) dataFile.length();
        int maxReadBufferSize = 1024 * 1024;

        String tfile = "temp-file-";
        long[] buffer = new long[(fileSize < maxReadBufferSize ? fileSize : maxReadBufferSize)];

        try {
            FileReader fr = new FileReader(dataFile);
            BufferedReader br = new BufferedReader(fr);
            int slices = (int) Math.ceil((double) fileSize / maxReadBufferSize);

            int i, j;
            i = j = 0;
            // Iterate through the elements in the file
            for (i = 0; i < slices; i++) {
                // Read M-element chunk at a time from the file
                for (j = 0; j < (maxReadBufferSize < fileSize ? maxReadBufferSize : fileSize); j++) {
                    String t = br.readLine();
                    if (t != null)
                        buffer[j] = Long.parseLong(t);
                    else
                        break;
                }
                // Sort M elements
                Arrays.sort(buffer);

            // Write the sorted numbers to temp file
            FileWriter fw = new FileWriter(tfile + Integer.toString(i) + ".txt");
            PrintWriter pw = new PrintWriter(fw);
            for (int k = 0; k < j; k++)
                pw.println(buffer[k]);

            pw.close();
            fw.close();
        }

        br.close();
        fr.close();

            // Now open each file and merge them, then write back to disk
            long[] topNums = new long[slices];
            BufferedReader[] brs = new BufferedReader[slices];

            for (i = 0; i < slices; i++)
            {
                brs[i] = new BufferedReader(new FileReader(tfile + Integer.toString(i) + ".txt"));
                String t = brs[i].readLine();
                if (t != null)
                    topNums[i] = Long.parseLong(t);
                else
                    topNums[i] = Long.MAX_VALUE;
            }

            FileWriter fw = new FileWriter("external-sorted.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (i = 0; i < fileSize; i++)
            {
                long min = topNums[0];
                int minFile = 0;

                for (j = 0; j < slices; j++)
                {
                    if (min > topNums[j])
                    {
                        min = topNums[j];
                        minFile = j;
                    }
                }

                pw.println(min);
                String t = brs[minFile].readLine();
                if (t != null)
                    topNums[minFile] = Long.parseLong(t);
                else
                    topNums[minFile] = Long.MAX_VALUE;

            }
            for (i = 0; i < slices; i++) {
                brs[i].close();
            }

            pw.close();
            fw.close();
        }
         catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    return new File("external-sorted.txt");
    }
}
