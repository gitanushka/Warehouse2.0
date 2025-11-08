package com.resilient.orderapp;

import com.resilient.orderapp.InventoryLoader;

import com.resilient.orderapp.RabbitMQConsumer;
import com.resilient.orderapp.RabbitMQProducer;
import com.resilient.orderapp.Order;
import java.sql.Timestamp;

public class Main {
    public static void main(String[] args) throws Exception {
        String csvPath = "/home/anushka.sharma/IdeaProjects/Warehouse/src/main/resources/inventory.csv"; // path to CSV file
        InventoryLoader.loadInventoryFromCSV(csvPath);

        // Step 2: Start RabbitMQ Consumer (listening for new orders)
        RabbitMQConsumer consumer = new RabbitMQConsumer();
        consumer.startListening(); // this will listen to "orders" queue in a separate thread

        // Step 3: Produce some test orders
        RabbitMQProducer producer = new RabbitMQProducer();


        // Current timestamp
        Timestamp now = new Timestamp(System.currentTimeMillis());
//        Order order1 = new Order(101, 1, "PENDING", now); // orderId=101, skuId=1, qty=2
//        Order order2 = new Order(102, 2, "PENDING", now); // orderId=102, skuId=2, qty=5
        Order order3 = new Order(103, 2, "PENDING", now);

//        producer.publishOrder(order1);
        producer.publishOrder(order3);

        System.out.println("✅ Orders sent to RabbitMQ.");
    }
}