package com.example.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
public class SendMqApplication {

	private final static String QUEUE_NAME = "mq_movctas";
	
	public static void main(String[] args) {
		SpringApplication.run(SendMqApplication.class, args);
		try {
			ConnectionFactory factory = new ConnectionFactory();
//			factory.setUsername("quest");
//			factory.setPassword("quest");	    
			factory.setHost("localhost");
			factory.setPort(5672);
			factory.setUsername("b2c_client");
			factory.setPassword("SuperPassword000");
//			factory.setHost("35.203.93.92");
//			factory.setPort(5672);
//factory.setHost("vhost1");

			Connection connection = factory.newConnection();			
			Channel channel = connection.createChannel();			
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			String message = "THE JOKER WAR HERE!->" + (new Date()).toString();
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			System.out.println("======================> [x] Sent '" + message + "'");
			
			channel.close();
			connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
