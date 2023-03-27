package io.ylab.intensive.lesson04.filesort;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {
  private DataSource dataSource;

  public FileSortImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) throws IOException, SQLException {
    // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ

    int BATCH_SIZE = 10000;
    String query = "insert into numbers (val) values (?)" ;

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      connection.setAutoCommit(false);

      try (FileInputStream fileInputStream = new FileInputStream(data);
           Scanner scanner = new Scanner(fileInputStream)) {

        int currentSize = 0;

        while (scanner.hasNextLong()) {

          String nextString = scanner.next();
          currentSize++;

          preparedStatement.setLong(1, Long.parseLong(nextString));
          preparedStatement.addBatch();

          if (currentSize == BATCH_SIZE || (!scanner.hasNextLong() && currentSize > 0)) {
            try {
              preparedStatement.executeBatch();
              connection.commit();
            } catch (BatchUpdateException ex) {
              connection.rollback();
              ex.printStackTrace();
            }

            currentSize = 0;
          }
        }
      }
    }

    Printer printer = new Printer();
    printer.printInFile(dataSource, "sql-with-batch-sorted.txt");

    return new File("sql-with-batch-sorted.txt");
  }
}
