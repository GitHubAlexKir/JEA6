package service;

import com.rabbitmq.client.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class RecevierControl {
 private final static String QUEUE_NAME = "hello";
 private Sender sender;

 @PostConstruct
 void init() {
  ConnectionFactory factory = new ConnectionFactory();
  try {
   sender = new Sender(QUEUE_NAME);
   factory.setHost("localhost");
   Connection connection = null;
   connection = factory.newConnection();
   Channel channel = null;
   channel = connection.createChannel();
   channel.queueDeclare(QUEUE_NAME, false, false, false, null);
   System.out.println(" [*] Waiting for messages.");
   Consumer consumer = new DefaultConsumer(channel) {
    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                               byte[] body) throws IOException {
     String message = new String(body, "UTF-8");
     System.out.println(" [JEA6-webshop] Received '" + message + "'");
     sender.send("we have a connection");
    }
   };
   channel.basicConsume(QUEUE_NAME, true, consumer);

  } catch (IOException | TimeoutException e) {
   e.printStackTrace();
  }
 }
}