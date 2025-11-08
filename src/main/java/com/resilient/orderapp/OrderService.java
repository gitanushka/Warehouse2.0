package com.resilient.orderapp;

import com.resilient.orderapp.InventoryDAO;
import com.resilient.orderapp.OrderDAO;
import com.resilient.orderapp.Order;

import java.sql.SQLException;

public class OrderService {

    private final OrderDAO orderDAO;
    private final InventoryDAO inventoryDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.inventoryDAO = new InventoryDAO();
    }

    /**
     * Processes an order:
     * - Inserts order in DB as PENDING
     * - Checks inventory
     * - Updates stock and order status (CONFIRMED / FAILED)
     *
     * @param order Order object to process
     */
    public void processOrder(Order order) {
        try {
            // Step 1: Create order in DB as PENDING
            int orderId = orderDAO.createOrder(order);
            System.out.println("Order ID " + orderId + " created with status PENDING.");

            // Step 2: Check available stock
            int availableQty = inventoryDAO.getStock(order.getSkuId());

            if (availableQty >= order.getQuantity()) {
                // Step 3: Reduce stock in inventory

                inventoryDAO.updateStock(order.getSkuId(), availableQty - order.getQuantity());

                // Step 4: Update order status to CONFIRMED
                orderDAO.updateOrderStatus(orderId, "CONFIRMED");
                System.out.println("Order ID " + orderId + " CONFIRMED. Stock updated.");

            } else {
                // Step 4: Update order status to FAILED
                orderDAO.updateOrderStatus(orderId, "FAILED");
                System.out.println("Order ID " + orderId + " FAILED. Insufficient stock.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Order processing failed due to database error.");
        }
    }
}
