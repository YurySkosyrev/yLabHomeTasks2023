package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

public class ApiApp {
  public static void main(String[] args) throws Exception {

    DataSource dataSource = DbUtil.buildDataSource();
    ConnectionFactory connectionFactory = initMQ();

    // Тут пишем создание PersonApi, запуск и демонстрацию работы

    PersonApiImpl personApi = new PersonApiImpl(dataSource, connectionFactory);

    System.out.println("Add some persons");
    personApi.savePerson(1L,"Petr", "Petrov", "Petrovich");
    personApi.savePerson(2L,"Pavel", "Pavlov", "Pavlovich");
    personApi.savePerson(3L,"Tamara", "Ivanova", "Ivanovna");
    personApi.savePerson(4L,"Nina", "Leonteva", "Alekseevna");
    System.out.println("findAll method");
    System.out.println(personApi.findAll());
    System.out.println("findPerson method id == 1");
    System.out.println(personApi.findPerson(1L));
    System.out.println();

    System.out.println("deletePerson method");
    System.out.println(personApi.findAll());
    System.out.println("Delete Person id == 2");
    personApi.deletePerson(2L);
    System.out.println("findPerson method id == 2");
    System.out.println(personApi.findPerson(2L));
    personApi.deletePerson(2L);
    System.out.println();

    System.out.println("Update Person");
    System.out.println(personApi.findPerson(1L));
    System.out.println("savePerson method");
    personApi.savePerson(1L,"Oleg", "Olegov", "Olegovich");
    System.out.println(personApi.findPerson(1L));
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
