package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageScheduler {

    private final MessageProcessor messageProcessor;
    private final ConnectionFactory connectionFactory;
    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "queue";

    public MessageScheduler(MessageProcessor messageProcessor, ConnectionFactory connectionFactory) {
        this.messageProcessor = messageProcessor;
        this.connectionFactory = connectionFactory;

        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message == null) {
                } else {
                    String received = new String(message.getBody());
                    messageProcessor.processSingleMessage(received);
                }
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
