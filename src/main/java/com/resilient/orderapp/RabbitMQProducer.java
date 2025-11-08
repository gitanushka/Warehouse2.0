package com.resilient.orderapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resilient.orderapp.Order;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQProducer {

    private final static String QUEUE_NAME = "orders_queue";

    public void publishOrder(Order order) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            // Convert order object to JSON
            ObjectMapper mapper = new ObjectMapper();
            String message = mapper.writeValueAsString(order);

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent order: " + message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
