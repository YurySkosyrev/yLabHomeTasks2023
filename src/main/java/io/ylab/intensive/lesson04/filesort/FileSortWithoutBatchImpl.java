package io.ylab.intensive.lesson04.filesort;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

public class FileSortWithoutBatchImpl implements FileSorter {
  private DataSource dataSource;

  public FileSortWithoutBatchImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public File sort(File data) throws IOException, SQLException {
    // ТУТ ПИШЕМ РЕАЛИЗАЦИЮ

    String query = "insert into numbers (val) values (?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      try (FileInputStream fileInputStream = new FileInputStream(data);
           Scanner scanner = new Scanner(fileInputStream)) {

        while (scanner.hasNextLong()) {

          String nextString = scanner.next();
          preparedStatement.setLong(1, Long.parseLong(nextString));
          preparedStatement.executeUpdate();

        }
      }
    }

    Printer printer = new Printer();
    printer.printInFile(dataSource, "sql-without-batch-sorted.txt");

    return new File("sql-without-batch-sorted.txt");
  }
}
