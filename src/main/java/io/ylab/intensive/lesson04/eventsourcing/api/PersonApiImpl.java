package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

  private DataSource dataSource;
  private ConnectionFactory connectionFactory;

  public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
    this.dataSource = dataSource;
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void deletePerson(Long personId) {
    
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {

  }

  @Override
  public Person findPerson(Long personId) throws SQLException {

    String query = "select * from person where person_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setLong(1, personId);

      Person person = new Person();
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        person.setId(rs.getLong("person_id"));
        person.setName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setMiddleName(rs.getString("middle_name"));
      }
      return person;
    }
  }

  @Override
  public List<Person> findAll() throws SQLException {
    List<Person> persons = new ArrayList<>();

    String query = "select * from person";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      Person person = new Person();
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        person.setId(rs.getLong("person_id"));
        person.setName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setMiddleName(rs.getString("middle_name"));

        persons.add(person);
      }
      return persons;
    }
  }
}
