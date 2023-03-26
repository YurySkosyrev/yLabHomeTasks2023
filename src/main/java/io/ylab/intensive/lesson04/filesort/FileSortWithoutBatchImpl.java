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

    String sortQuery = "select * from numbers order by val desc";
    PrintWriter printWriter = new PrintWriter("sql-without-batch-sorted.txt");
    String cleanQuery = "delete from numbers";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sortQuery);
        PreparedStatement deleteStatement = connection.prepareStatement(cleanQuery)) {

      ResultSet rs = preparedStatement.executeQuery();

      try {
        while (rs.next()) {
          printWriter.println(rs.getString(1));
        }
      } finally {
        printWriter.close();
      }

      deleteStatement.executeUpdate();

    }

    return new File("sql-without-batch-sorted.txt");
  }
}
