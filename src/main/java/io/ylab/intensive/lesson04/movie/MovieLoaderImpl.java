package io.ylab.intensive.lesson04.movie;

import com.edu.ylab.homework3.Task3.Entity.Employee;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;
import javax.sql.DataSource;

public class MovieLoaderImpl implements MovieLoader {
  private DataSource dataSource;

  public MovieLoaderImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void loadData(File file) {
    // РЕАЛИЗАЦИЮ ПИШЕМ ТУТ

    try (FileInputStream fileInputStream = new FileInputStream(file);
         Scanner scanner = new Scanner(fileInputStream)){
      scanner.nextLine();
      scanner.nextLine();

      while (scanner.hasNextLine()) {

        String[] data = scanner.nextLine().split(";");

        Movie movie = new Movie();
        movie.setYear(Integer.parseInt(data[0]));
        movie.setLength(Integer.parseInt(data[1]));
        movie.setTitle(data[2]);
        movie.setSubject(data[3]);
        movie.setActors(data[4]);
        movie.setActress(data[5]);
        movie.setDirector(data[6]);
        movie.setPopularity(Integer.parseInt(data[7]));
        movie.setAwards(data[6].equals("No") ? false : true);

        saveMovie(movie);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void saveMovie(Movie movie) throws SQLException {
    String insertQuery = "insert into movie (year, length, title, subject, actors, actress, " +
            "director, popularity, awards) values ?, ?, ?, ?, ?, ?, ?, ?, ?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){
      preparedStatement.setInt(1, movie.getYear());

      if (movie.getLength() == null) {
        preparedStatement.setNull(2, Types.INTEGER);
      } else {
        preparedStatement.setInt(2, movie.getLength());
      }

      preparedStatement.setString(3, movie.getTitle());
      preparedStatement.setString(4, movie.getSubject());
      preparedStatement.setString(5, movie.getActors());
      preparedStatement.setString(6, movie.getActress());
      preparedStatement.setString(7, movie.getDirector());
      preparedStatement.setInt(8, movie.getPopularity());
      preparedStatement.setBoolean(9,movie.getAwards());

      preparedStatement.executeUpdate();
    }
  }
}
