package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Component
public class QueueProcessor {

    private final DbClient dbClient;
    private final ConnectionFactory connectionFactory;

    private final String EXCHANGE_NAME = "output_exc";
    private final String QUEUE_NAME = "output";

    public QueueProcessor(DbClient dbClient, ConnectionFactory connectionFactory) {
        this.dbClient = dbClient;
        this.connectionFactory = connectionFactory;
    }

    public void filterMessage(String message) {

        String messageForSet = message.replace("\r", "");
        StringBuilder messageSB = new StringBuilder(messageForSet);

        Set<String> wordsOfMessage = Arrays.stream(messageForSet.split(" "))
                .map(word -> word.replaceAll("[,.;!?]", ""))
                .collect(Collectors.toSet());

        for (String word : wordsOfMessage) {
            if(dbClient.isWordInDB(word.toLowerCase())) {
                String goodWord = word.substring(0,1)
                        + "*".repeat(word.length()-2)
                        + word.substring(word.length()-1);
                replaceWordSb(messageSB, word, goodWord);
            }
        }

        sendMessage(messageSB.toString(), EXCHANGE_NAME, QUEUE_NAME);
    }

    public void sendMessage(String message, String exc, String queue) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exc, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exc, "*");

            channel.basicPublish(exc, "*", null, message.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void replaceWordSb(StringBuilder sb, String word, String replaceWord) {
        int index = sb.indexOf(word);

        while(index != -1) {
            sb.replace(index, index + word.length(), replaceWord);
            index += replaceWord.length();
            index = sb.indexOf(word, index);
        }
    }
}
