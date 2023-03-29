package io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class MovieLoaderBatchImpl implements MovieLoader {
  private DataSource dataSource;

  public MovieLoaderBatchImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

    @Override
    public void loadData(File file) {
        // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ

        int BATCH_SIZE = 500;
        String insertQuery = "insert into movie (year, length, title, subject, actors, actress, " +
                "director, popularity, awards) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            connection.setAutoCommit(false);

            try (FileInputStream fileInputStream = new FileInputStream(file);
                 Scanner scanner = new Scanner(fileInputStream)) {

                scanner.nextLine();
                scanner.nextLine();

                int currentSize = 0;

                while (scanner.hasNextLine()) {

                    String[] data = scanner.nextLine().split(";");
                    currentSize++;

                    Movie movie = getMovie(data);
                    setPreparedStatement(preparedStatement, movie);
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
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void setPreparedStatement(PreparedStatement preparedStatement, Movie movie) throws SQLException {
        if (movie.getYear() == -1) {
            preparedStatement.setNull(1, Types.INTEGER);
        } else {
            preparedStatement.setInt(1, movie.getYear());
        }

        if (movie.getLength() == -1) {
            preparedStatement.setNull(2, Types.INTEGER);
        } else {
            preparedStatement.setInt(2, movie.getLength());
        }

        preparedStatement.setString(3, movie.getTitle());
        preparedStatement.setString(4, movie.getSubject());
        preparedStatement.setString(5, movie.getActors());
        preparedStatement.setString(6, movie.getActress());
        preparedStatement.setString(7, movie.getDirector());

        if (movie.getPopularity() == -1) {
            preparedStatement.setNull(8, Types.INTEGER);
        } else {
            preparedStatement.setInt(8, movie.getPopularity());
        }

        preparedStatement.setBoolean(9, movie.getAwards());
    }

    private Movie getMovie(String[] data) {
        Movie movie = new Movie();
        movie.setYear(data[0] == "" ? -1 : Integer.parseInt(data[0]));
        movie.setLength(data[1] == "" ? -1 : Integer.parseInt(data[1]));
        movie.setTitle(data[2]);
        movie.setSubject(data[3]);
        movie.setActors(data[4]);
        movie.setActress(data[5]);
        movie.setDirector(data[6]);
        movie.setPopularity(data[7] == "" ? -1 : Integer.parseInt(data[7]));
        movie.setAwards(data[8].equals("No") ? false : true);
        return movie;
    }
}
