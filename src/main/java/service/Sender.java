package service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {
 private String QUEUE_NAME;
 private Channel channel;

 public Sender(String QUEUE_NAME)  {
  ConnectionFactory factory = new ConnectionFactory();
  this.QUEUE_NAME = QUEUE_NAME;
  factory.setHost("localhost");
  Connection connection = null;
  try {
   connection = factory.newConnection();
   channel = connection.createChannel();
   channel.queueDeclare(QUEUE_NAME, false, false, false, null);
  } catch (IOException | TimeoutException e) {
   e.printStackTrace();
  }
 }
 public void send(String json)  {
  try {
   channel.basicPublish("", QUEUE_NAME, null, json.getBytes());
  } catch (IOException e) {
   e.printStackTrace();
  }
  System.out.println(" [JEA6-Webshop] Sent '" + json + "'");
 }

}