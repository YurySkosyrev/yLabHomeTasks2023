package io.ylab.intensive.lesson04.eventsourcing.db;

import io.ylab.intensive.lesson04.eventsourcing.Person;

import java.sql.SQLException;

public interface PersonDb {
    void deletePerson(Long personId) throws SQLException;

    void savePerson(Person person) throws SQLException;
}
