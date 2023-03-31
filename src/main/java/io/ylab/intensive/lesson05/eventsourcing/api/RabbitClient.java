package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.RequestToDB;
import org.springframework.stereotype.Component;

@Component
public class RabbitClient {

    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "queue";

    private final ConnectionFactory connectionFactory;

    public RabbitClient(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void deletePerson(Long personId) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestToDB request = new RequestToDB("delete", personId.toString());

            String deleteMessage = objectMapper.writeValueAsString(request);
            sendMessage(deleteMessage);
        } catch (JacksonException e) {
            e.printStackTrace();
        }
    }

    public void savePerson(Long personId, String firstName, String lastName, String middleName) {

        try {
            Person person = new Person(personId, firstName, lastName, middleName);

            ObjectMapper objectMapper = new ObjectMapper();
            String body = objectMapper.writeValueAsString(person);
            RequestToDB request = new RequestToDB("insert", body);

            String saveMessage = objectMapper.writeValueAsString(request);
            sendMessage(saveMessage);
        } catch (JacksonException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
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
