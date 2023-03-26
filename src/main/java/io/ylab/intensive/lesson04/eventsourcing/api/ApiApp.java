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

    String exchangeName = "exc";
    String queueName = "queue";

    ConnectionFactory connectionFactory = initMQ();
    // Тут пишем создание PersonApi, запуск и демонстрацию работы

    PersonApiImpl personApi = new PersonApiImpl(dataSource, connectionFactory);

    System.out.println(personApi.findAll());
    System.out.println(personApi.findPerson(1L));


//    try(Connection connection = connectionFactory.newConnection();
//        Channel channel = connection.createChannel()){
//      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC);
//      channel.queueDeclare(queueName, true, false, false, null);
//      channel.queueBind(queueName,exchangeName,"*");
//
//      String message = "V rot ebat";
//      channel.basicPublish(exchangeName, "*", null, message.getBytes("UTF-8"));
//      System.out.println(" [x] Sent '" + message + "'");
//    }
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
