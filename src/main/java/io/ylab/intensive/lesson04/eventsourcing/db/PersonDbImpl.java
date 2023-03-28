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

    private final String exchangeName = "exc";
    private final String queueName = "queue";

    public PersonDbImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) throws SQLException {
        String query = "delete from person where person_id=?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, personId);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void savePerson(Person person) throws SQLException {

        String query = "insert into person (person_id, first_name, last_name, middle_name) values (?, ?, ?, ?) " +
                "on conflict (person_id) do update set first_name = ?, last_name = ?, middle_name = ?";

        try(Connection connection = dataSource.getConnection();
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
