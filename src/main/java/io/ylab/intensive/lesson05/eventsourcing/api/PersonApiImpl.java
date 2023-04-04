package io.ylab.intensive.lesson05.eventsourcing.api;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import io.ylab.intensive.lesson05.eventsourcing.db.DBClient;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * Тут пишем реализацию
 */
@Component
public class PersonApiImpl implements PersonApi {

  private final DBClient dbClient;
  private final RabbitClient rabbitClient;

  public PersonApiImpl(DBClient dbClient, RabbitClient rabbitClient) {
    this.dbClient = dbClient;
    this.rabbitClient = rabbitClient;
  }

  @Override
  public void deletePerson(Long personId) {
    rabbitClient.deletePerson(personId);
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    rabbitClient.savePerson(personId, firstName, lastName, middleName);
  }

  @Override
  public Person findPerson(Long personId) throws SQLException {
    return dbClient.findPerson(personId);
  }

  @Override
  public List<Person> findAll() throws SQLException {
    return dbClient.findAll();
  }

}
