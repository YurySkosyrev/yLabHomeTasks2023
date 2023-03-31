package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageScheduler {

    private final MessageProcessor messageProcessor;
    private final ConnectionFactory connectionFactory;
    String queueName = "queue";

    public MessageScheduler(MessageProcessor messageProcessor, ConnectionFactory connectionFactory) {
        this.messageProcessor = messageProcessor;
        this.connectionFactory = connectionFactory;
    }

    public void start() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
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
