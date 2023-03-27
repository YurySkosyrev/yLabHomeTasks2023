package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Задание 3
 * Реализовать интерфейс
 * public interface FileSorter {
 * File sortFile(File dataFile) throws Exception;
 * }
 * Реализация интерфейса получает на вход файл, состоящий из чисел (long),
 * разделенных переносом строки и возвращает файл, в котором эти числа
 * отсортированы в порядке убывания.
 * 1. Можно считать, что максимальный размер файла - 1000000 чисел
 * 2. Сортировку необходимо реализовать средствами БД
 * 3. Работа с БД - средствами JDBC
 * 4. При вставке данных обязательно использовать batch-processing. Разобраться
 * что это такое, для чего используется и как реализовать
 * 5. Необязательно. Реализовать версию без batch-processing, сравнить
 * производительность
 */


public class FileSorterTest {
  public static void main(String[] args) throws SQLException, IOException {
    DataSource dataSource = initDb();

    File dataFile = new Generator().generate("data.txt", 1_000);
    File data = new File("data.txt");

    FileSorter fileSorter = new FileSortImpl(dataSource);

    long start = System.currentTimeMillis();
    File res = fileSorter.sort(data);
    long finish = System.currentTimeMillis();
    System.out.println("Execution time with batch-processing: " + (finish-start));
    System.out.println(new Validator(res).isSorted()); // true
    System.out.println(new Validator(res).isHashEquals(data)); // true

    fileSorter = new FileSortWithoutBatchImpl(dataSource);

    start = System.currentTimeMillis();
    res = fileSorter.sort(data);
    finish = System.currentTimeMillis();
    System.out.println("Execution time without batch-processing: " + (finish-start));
    System.out.println(new Validator(res).isSorted()); // true
    System.out.println(new Validator(res).isHashEquals(data)); // true
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
}
