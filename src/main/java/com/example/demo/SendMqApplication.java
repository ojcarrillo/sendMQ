package com.example.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeoutException;

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
			/* configura la conexion al servidor de colas */
			ConnectionFactory factory = new ConnectionFactory();
			factory.setUsername("b2c_client");
			factory.setPassword("SuperPassword000");
			factory.setHost("localhost");
			factory.setPort(5672);
			/* abre la conexion */
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, true, false, false, null);
			/* genera el mensaje */
			String message = "";//Utils.getRandomInt(3)
			switch (3) {
			case 1:
				message = Gendata.getDataInvoice();
				break;
			case 2:
				message = Gendata.getDataBill();
				break;
			default:
				message = Gendata.getDataReceipt();
				break;
			}
			/* publica el mensaje en la cola */
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
			/* cierra la conexion al servidor de colas */
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
