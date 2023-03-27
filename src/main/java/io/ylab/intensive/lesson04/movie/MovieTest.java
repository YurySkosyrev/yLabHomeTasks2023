package io.ylab.intensive.lesson04.movie;

import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

/**
 * Задание 1
 * Скачать файл https://perso.telecom-paristech.fr/eagan/class/igr204/data/film.csv
 * В файле содержать данные о фильмах
 * Необходимо
 * 1. Реализовать код, читающий данные из файла и записывающий в таблицу через
 * JDBC. Для добавления данных использовать PreparedStatement.
 * В работе необходимо использовать следующий класс:
 * class Movie {
 * private Integer year;
 * private Integer length;
 * private String title;
 * private String subject;
 * private String actors;
 * private String actress;
 * private String director;
 * private Integer popularity;
 * private Boolean awards;
 * }
 * Данные, считываемые из файла должны быть упакованы в экземпляр
 * указанного класса. Затем этот экземпляр должен передаваться коду, который
 * будет отвечать за сохранение данных в БД
 * Обратить внимание, что в файле могут некоторые значения могут
 * отсутствовать. В таком случает надо вызывать
 * preparedStatement.setNull(<index>, java.sql.Types.<тип>)
 */

public class MovieTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    MovieLoader movieLoader = new MovieLoaderImpl(dataSource);

    File dataFile = new File("film.csv");
    movieLoader.loadData(dataFile);

    /**
     * Тут написать в комментариях запрос получения всех
     * SELECT subject, COUNT(*) as count FROM movie GROUP BY subject
     */
  }

  private static DataSource initDb() throws SQLException {
    String createMovieTable = "drop table if exists movie;"
                                  + "CREATE TABLE IF NOT EXISTS movie (\n"
                                  + "\tid bigserial NOT NULL,\n"
                                  + "\t\"year\" int4,\n"
                                  + "\tlength int4,\n"
                                  + "\ttitle varchar,\n"
                                  + "\tsubject varchar,\n"
                                  + "\tactors varchar,\n"
                                  + "\tactress varchar,\n"
                                  + "\tdirector varchar,\n"
                                  + "\tpopularity int4,\n"
                                  + "\tawards bool,\n"
                                  + "\tCONSTRAINT movie_pkey PRIMARY KEY (id)\n"
                                  + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMovieTable, dataSource);
    return dataSource;
  }
}
