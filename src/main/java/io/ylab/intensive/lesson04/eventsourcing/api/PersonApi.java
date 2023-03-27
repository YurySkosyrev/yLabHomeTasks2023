package io.ylab.intensive.lesson04.eventsourcing.api;

import java.sql.SQLException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.ylab.intensive.lesson04.eventsourcing.Person;

public interface PersonApi {
  void deletePerson(Long personId);

  void savePerson(Long personId, String firstName, String lastName, String middleName) throws JsonProcessingException;

  Person findPerson(Long personId) throws SQLException;

  List<Person> findAll() throws SQLException;
}
