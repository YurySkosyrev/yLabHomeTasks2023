package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

/**
 * Задание 2
 * Необходимо реализовать Map, хранящий свое состояние исключительно в базе
 * данных. То есть, любое изменение данных Map (добавление и удаление), а также
 * получение данных должно транслироваться в соответствующие SQL запросы. Данные
 * необходимо хранить в таблице следующего вида
 * CREATE TABLE persistent_map (
 * map_name varchar,
 * KEY varchar,
 * value varchar
 * );
 * name - имя экземпляра Map
 * key - ключ в экземпляре Map
 * value - значение, соответствующее ключу в текущем экземпляре Map
 * Реализация состоит в реализации следующего интерфейса
 * public interface PersistentMap {
 * void init(String name);
 * boolean containsKey(String key) throws SQLException;
 * List<String> getKeys() throws SQLException;
 * String get(String key) throws SQLException;
 * void remove(String key) throws SQLException;
 * void put(String key, String value) throws SQLException;
 * void clear() throws SQLException;
 * }
 * init. Метод используется для инициализации нового экземпляра Map. Принимает имя
 * текущего экземпляра. Данные всех экземпляров хранятся в одной таблице, и имя
 * используется для того, чтобы отделять данные одного экземпляра от данных другого
 * containsKey. Возвращает true тогда и только тогда, когда существует значение,
 * связанное с данным ключом, false - в противном случае
 * getKeys. Возвращает список ключей, для которых есть значения в БД
 * get. Возвращает значение, связанное с переданным ключом
 * remove. Удаляет пару ключ/значение из Map
 * put. Служит для добавления новой пары ключ-значение. В своей работе сначала
 * удаляет существую пару из Map (если она есть), а затем добавляет новую
 * clear. Удаляет все данные из текущего экземпляра Map
 * Допущение: можно считать, что одновременно только одно приложение будет
 * работать с конкретным экземпляром. То есть, соблюдение строгой транзакционности и
 * реализация многопоточной работы не обязательны!
 * Создание таблицы производится отдельно. То есть в код создание таблицы добавлять
 * не нужно!
 */

public class PersistenceMapTest {
  public static void main(String[] args) throws SQLException {

    DataSource dataSource = initDb();
    PersistentMap persistentMap = new PersistentMapImpl(dataSource);

    // Написать код демонстрации работы

    persistentMap.init("newMap");
    System.out.println("newMap init");
    persistentMap.put("key1", "value1");
    persistentMap.put("key2", "value2");
    persistentMap.put("key3", "value3");
    System.out.println("put some values in newMap");
    System.out.println("newMap: " + persistentMap);
    System.out.println();

    System.out.println("Is newMap contain key1 : " + persistentMap.containsKey("key1")); //true
    System.out.println("Is newMap contain key4 : " + persistentMap.containsKey("key4")); //false
    System.out.println("newMap keys : " + persistentMap.getKeys());
    System.out.println("Value of key key2 is " + persistentMap.get("key2"));
    System.out.println("Value of key key4 is " + persistentMap.get("key4"));
    System.out.println();

    System.out.println("Remove key2 from newMap");
    persistentMap.remove("key2");
    System.out.println("newMap: " + persistentMap);
    System.out.println("Put key4 to newMap");
    persistentMap.put("key4", "new value");
    System.out.println("newMap: " + persistentMap);
    System.out.println("Update key4 in newMap");
    persistentMap.put("key4", "value4");
    System.out.println("newMap: " + persistentMap);

    persistentMap.init("anotherMap");
    System.out.println("another Map init");
    persistentMap.put("key11", "value11");
    persistentMap.put("key21", "value22");
    System.out.println("anotherMap: " + persistentMap);

    System.out.println("Clean anotherMap");
    persistentMap.clear();
    System.out.println("anotherMap: " + persistentMap);

    persistentMap.init("newMap");
    System.out.println("Clean newMap");
    persistentMap.clear();
    System.out.println("newMap: " + persistentMap);
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
