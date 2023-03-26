package io.ylab.intensive.lesson04.filesort;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

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

    fileSorter = new FileSortWithoutBatchImpl(dataSource);
    start = System.currentTimeMillis();
    res = fileSorter.sort(data);
    finish = System.currentTimeMillis();
    System.out.println("Execution time without batch-processing: " + (finish-start));
    System.out.println(new Validator(res).isSorted()); // true
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
