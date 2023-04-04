package io.ylab.intensive.lesson05.eventsourcing.db;


import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBClient {

    private final DataSource dataSource;

    public DBClient(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void deletePerson(Long personId) throws SQLException {
        String query = "delete from person where person_id=?";

        try(java.sql.Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, personId);

            if (preparedStatement.executeUpdate() == 0) {
                System.err.println("Была попытка удаления - запись по id " + personId + " не найдена");
            }

        }
    }


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

    public Person findPerson(Long personId) throws SQLException {

        String query = "select * from person where person_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, personId);

            Person person = new Person();
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                person.setId(rs.getLong("person_id"));
                person.setName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setMiddleName(rs.getString("middle_name"));
                return person;
            }
            return null;
        }
    }


    public List<Person> findAll() throws SQLException {
        List<Person> persons = new ArrayList<>();

        String query = "select * from person order by person_id";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("person_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String middle_name = rs.getString("middle_name");

                Person person = new Person(id, first_name, last_name, middle_name);
                persons.add(person);
            }
            return persons.size() > 0 ? persons : null;
        }
    }

}
