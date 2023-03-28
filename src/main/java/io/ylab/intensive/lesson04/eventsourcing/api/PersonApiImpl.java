package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.RequestToDB;

import javax.sql.DataSource;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

  private DataSource dataSource;
  private ConnectionFactory connectionFactory;

  private final String EXCHANGE_NAME = "exc";
  private final String QUEUE_NAME = "queue";

  public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
    this.dataSource = dataSource;
    this.connectionFactory = connectionFactory;
  }

  @Override
  public void deletePerson(Long personId) {

    try {
      if (this.findPerson(personId) != null) {

          ObjectMapper objectMapper = new ObjectMapper();
          RequestToDB request = new RequestToDB("delete", personId.toString());

          String deleteMessage = objectMapper.writeValueAsString(request);
          sendMessage(deleteMessage);
      } else {
        System.err.println("Была попытка удаления - запись по id " + personId + " не найдена");
      }
    } catch (SQLException | JacksonException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {

    try {
      Person person = new Person(personId, firstName, lastName, middleName);

      ObjectMapper objectMapper = new ObjectMapper();
      String body = objectMapper.writeValueAsString(person);
      RequestToDB request = new RequestToDB("insert", body);

      String saveMessage = objectMapper.writeValueAsString(request);
      sendMessage(saveMessage);
    }
    catch (JacksonException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Person findPerson(Long personId) throws SQLException {

    String query = "select * from person where person_id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      preparedStatement.setLong(1, personId);

      Person person = new Person();
      ResultSet rs = preparedStatement.executeQuery();
      if (rs.next()) {
        person.setId(rs.getLong("person_id"));
        person.setName(rs.getString("first_name"));
        person.setLastName(rs.getString("last_name"));
        person.setMiddleName(rs.getString("middle_name"));
        return person;
      }
      return null;
    }
  }

  @Override
  public List<Person> findAll() throws SQLException {
    List<Person> persons = new ArrayList<>();

    String query = "select * from person order by person_id";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        Long id = rs.getLong("person_id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String middle_name = rs.getString("middle_name");

        Person person = new Person(id, first_name, last_name, middle_name);
        persons.add(person);
      }
      return persons.size() > 0 ? persons : null;
    }
  }

  private void sendMessage(String message) {
    try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);
      channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");

      channel.basicPublish(EXCHANGE_NAME, "*", null, message.getBytes("UTF-8"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
