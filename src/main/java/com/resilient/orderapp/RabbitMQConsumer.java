package com.resilient.orderapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resilient.orderapp.Order;
import com.resilient.orderapp.OrderService;
import com.rabbitmq.client.*;

        import java.io.IOException;

public class RabbitMQConsumer {

    private final static String QUEUE_NAME = "orders_queue";
    private final OrderService orderService = new OrderService();

    public void startListening() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for orders. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            // Convert JSON to Order object
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(message, Order.class);

            System.out.println(" [x] Received order: " + message);

            // Process the order
            orderService.processOrder(order);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
