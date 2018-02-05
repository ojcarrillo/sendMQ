package com.example.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendMQ {
	
	private final static String QUEUE_NAME = "mq_movctas";

	  public static void main(String[] argv) throws Exception {
		  
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setUsername("test");
	    factory.setPassword("test");	    
	    factory.setHost("35.203.93.92");
	    factory.setPort(5672);
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    String message = "THE JOKER WAR HERE!";
	    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	    System.out.println(" [x] Sent '" + message + "'");

	    channel.close();
	    connection.close();
	  }

}
