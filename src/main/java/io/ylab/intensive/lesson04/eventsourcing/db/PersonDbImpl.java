package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonDbImpl implements PersonDb{

    private DataSource dataSource;
    private ConnectionFactory connectionFactory;

    private final String EXCHANGE_NAME = "exc";
    private final String QUEUE_NAME = "queue";

    public PersonDbImpl(DataSource dataSource,
                        ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
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

    @Override
    public void deletePerson(Long personId) throws SQLException {
        String query = "delete from person where person_id=?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, personId);

            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Была попытка удаления - запись по id " + personId + " не найдена");
            }
        }
    }

    @Override
    public void savePerson(Person person) throws SQLException {

        if(person.getId() == null) {
            System.err.println("Была попытка вставить запись с id = null");
        } else if (person.getName() == null && person.getLastName()==null && person.getMiddleName()==null) {
            System.err.println("Была попытка вставить запись со всеми полями null");
        } else {
            String query = "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?) " +
                    "on conflict (person_id) do update set first_name = ?, last_name = ?, middle_name = ?";

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setLong(1, person.getId());
                preparedStatement.setString(2, person.getName());
                preparedStatement.setString(3, person.getLastName());
                preparedStatement.setString(4, person.getMiddleName());

                preparedStatement.setString(5, person.getName());
                preparedStatement.setString(6, person.getLastName());
                preparedStatement.setString(7, person.getMiddleName());

                preparedStatement.executeUpdate();
            }
        }
    }

}
