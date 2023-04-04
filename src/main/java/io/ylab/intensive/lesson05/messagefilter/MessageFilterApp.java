package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class MessageFilterApp {
  public static void main(String[] args) throws SQLException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    QueueScheduler queueScheduler = applicationContext.getBean(QueueScheduler.class);
    queueScheduler.start();
  }
}
