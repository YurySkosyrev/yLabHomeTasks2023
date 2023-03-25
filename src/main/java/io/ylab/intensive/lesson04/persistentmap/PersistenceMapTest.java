package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);
    // Написать код демонстрации работы
    persistentMap.init("newMap");
    persistentMap.put("key1", "value1");
    persistentMap.put("key2", "value2");
    persistentMap.put("key3", "value3");
    System.out.println(persistentMap.containsKey("key2"));
    System.out.println(persistentMap.getKeys());
    System.out.println(persistentMap.get("key2"));
    persistentMap.put("key3", "new value");
    System.out.println(persistentMap.get("key3"));
    persistentMap.remove("key2");
    System.out.println(persistentMap.getKeys());
  }
  
  public static DataSource initDb() throws SQLException {
    String createMapTable = "" 
                                + "drop table if exists persistent_map; " 
                                + "CREATE TABLE if not exists persistent_map (\n"
                                + "   map_name varchar,\n"
                                + "   KEY varchar,\n"
                                + "   value varchar,\n"
                                + "PRIMARY KEY(map_name, KEY));";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createMapTable, dataSource);
    return dataSource;
  }
}
