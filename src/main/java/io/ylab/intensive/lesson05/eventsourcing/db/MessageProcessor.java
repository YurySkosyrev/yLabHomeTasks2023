package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.RequestToDB;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class MessageProcessor {

    private final DBClient dbClient;

    public MessageProcessor(DBClient dbClient) {
        this.dbClient = dbClient;
    }

    public void processSingleMessage(String received) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RequestToDB response = objectMapper.readValue(received, RequestToDB.class);
            if (response.getMethod().equals("delete")) {
                dbClient.deletePerson(Long.parseLong(response.getBody()));
            } else {
                Person person = objectMapper.readValue(response.getBody(), Person.class);
                dbClient.savePerson(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
