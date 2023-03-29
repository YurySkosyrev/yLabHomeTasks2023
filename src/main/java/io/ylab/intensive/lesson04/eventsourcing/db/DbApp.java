package io.ylab.intensive.lesson04.eventsourcing.db;

import java.sql.SQLException;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.RequestToDB;

public class DbApp {
  public static void main(String[] args) throws Exception {
    DataSource dataSource = initDb();
    ConnectionFactory connectionFactory = initMQ();

    String queueName = "queue";

    // тут пишем создание и запуск приложения работы с БД

    PersonDb personDb = new PersonDbImpl(dataSource, connectionFactory);
    ObjectMapper objectMapper = new ObjectMapper();

    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()){
      while (!Thread.currentThread().isInterrupted()) {
          GetResponse message = channel.basicGet(queueName, true);
          if (message == null) {
          } else {
            String received = new String(message.getBody());
            RequestToDB response = objectMapper.readValue(received, RequestToDB.class);

            try {
              if (response.getMethod().equals("delete")) {
                personDb.deletePerson(Long.parseLong(response.getBody()));
              } else {
                Person person = objectMapper.readValue(response.getBody(), Person.class);
                personDb.savePerson(person);
              }
            } catch (SQLException e) {
              e.printStackTrace();
            }
          }
      }
    }
  }
  
  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
  
  private static DataSource initDb() throws SQLException {
    String ddl = "" 
                     + "drop table if exists person;" 
                     + "create table person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
