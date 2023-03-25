package io.ylab.intensive.lesson04.persistentmap;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {
  
  private DataSource dataSource;
  private String activeMap;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    activeMap = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {

    String query = "select from persistent_map where map_name=? and KEY=?";
    Boolean isContainsKey = false;

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, activeMap);
      preparedStatement.setString(2, key);

      ResultSet rs = preparedStatement.executeQuery();
      isContainsKey = rs.next();
    }
    return isContainsKey;
  }

  @Override
  public List<String> getKeys() throws SQLException {

    List<String> keys = new ArrayList<>();

    String query = "select KEY from persistent_map where map_name=?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, activeMap);

      ResultSet rs = preparedStatement.executeQuery();

      while (rs.next()){
        keys.add(rs.getString(1));
      }
    }
    return keys;
  }

  @Override
  public String get(String key) throws SQLException {

    String query = "select value from persistent_map where map_name=? and KEY=?";

    String result = null;

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, activeMap);
      preparedStatement.setString(2, key);

      ResultSet rs = preparedStatement.executeQuery();

      if(rs.next()) {
        result = rs.getString(1);
      }
    }
    return result;
  }

  @Override
  public void remove(String key) throws SQLException {

    String query = "delete from persistent_map where KEY=?";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setString(1, key);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void put(String key, String value) throws SQLException {


    String query = "insert into persistent_map (map_name, KEY, value) values (?, ?, ?) " +
            "on conflict (map_name, KEY) do update set value = ?";

//    String query = "insert into persistent_map (map_name, KEY, value) values (?, ?, ?)";

    try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
      preparedStatement.setString(1, activeMap);
      preparedStatement.setString(2, key);
      preparedStatement.setString(3, value);
      preparedStatement.setString(4, value);

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public void clear() throws SQLException {
    String query = "delete from persistent_map where map_name=?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setString(1, activeMap);

      preparedStatement.executeQuery();
    }
  }
}
