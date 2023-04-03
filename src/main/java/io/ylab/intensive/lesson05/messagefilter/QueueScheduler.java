package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import io.ylab.intensive.lesson05.eventsourcing.db.DBClient;
import io.ylab.intensive.lesson05.eventsourcing.db.MessageProcessor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class QueueScheduler {

    private final ConnectionFactory connectionFactory;
    private final QueueProcessor queueProcessor;
    private final DbClient dbClient;
    private final String INPUT_EXCHANGE = "input_exc";
    private final String INPUT_QUEUE = "input";

    public QueueScheduler(ConnectionFactory connectionFactory, QueueProcessor queueProcessor, DbClient dbClient) {
        this.connectionFactory = connectionFactory;
        this.queueProcessor = queueProcessor;
        this.dbClient = dbClient;

        try (com.rabbitmq.client.Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(INPUT_EXCHANGE, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(INPUT_QUEUE, true, false, false, null);
            channel.queueBind(INPUT_QUEUE, INPUT_EXCHANGE, "*");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String queueName = "input";

    public void start() {

        File dictFile = new File("dictionary.file");
        dbClient.init();
        dbClient.load(dictFile);

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()){
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse response = channel.basicGet(queueName, true);
                if (response == null) {
                } else {
                    String message = new String(response.getBody());
                    queueProcessor.filterMessage(message);
                }
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

}
