package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class QueueProcessor {

    private final DbClient dbClient;
    private final ConnectionFactory connectionFactory;

    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "output";

    public QueueProcessor(DbClient dbClient, ConnectionFactory connectionFactory) {
        this.dbClient = dbClient;
        this.connectionFactory = connectionFactory;
    }

    public void filterMessage(String message) {
        String goodMessage = message;
        sendMessage(goodMessage);
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
