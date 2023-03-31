package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();
    PersonApi personApi = applicationContext.getBean(PersonApi.class);
    // пишем взаимодействие с PersonApi

    System.out.println("Add some persons");
    personApi.savePerson(1L,"Petr", "Petrov", "Petrovich");
    personApi.savePerson(2L,"Pavel", "Pavlov", "Pavlovich");
    personApi.savePerson(3L,"Tamara", "Ivanova", "Ivanovna");
    personApi.savePerson(4L,"Nina", "Leonteva", "Alekseevna");

    personApi.savePerson(null,"Nina", "Leonteva", "Alekseevna");
    personApi.savePerson(5L,null, null, null);

    System.out.println("Find all persons");
    System.out.println(personApi.findAll());
    System.out.println("Find person with id == 1");
    System.out.println(personApi.findPerson(1L));
    System.out.println();

    System.out.println("Delete person");
    System.out.println(personApi.findAll());
    System.out.println("Delete person with id == 2");
    personApi.deletePerson(2L);
    System.out.println("Find person with id == 2");
    Thread.currentThread().sleep(100);
    System.out.println(personApi.findPerson(2L));
    personApi.deletePerson(2L);
    System.out.println();

    System.out.println("Update Person");
    System.out.println("Find person with id == 1");
    System.out.println(personApi.findPerson(1L));
    System.out.println("Update/insert person with id = 1");
    personApi.savePerson(1L,"Oleg", "Olegov", "Olegovich");
    Thread.currentThread().sleep(100);
    System.out.println("Find person with id == 1");
    System.out.println(personApi.findPerson(1L));
    personApi.savePerson(1L,"Oleg", "Olegov", "Olegovich");
    personApi.savePerson(1L,"Oleg", "Olegov", "Olegovich");
    personApi.savePerson(1L,"Oleg", "Olegov", "Olegovich");
  }
}
