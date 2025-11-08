package com.resilient.orderapp;

import com.resilient.orderapp.Order;
import com.resilient.orderapp.DBConnection;

import java.sql.*;

public class OrderDAO {

    /**
     * Inserts a new order into the orders table as PENDING.
     * Returns the generated order_id.
     */
    public int createOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (sku_id, quantity, status, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getSkuId());
            stmt.setInt(2, order.getQuantity());
            stmt.setString(3, order.getStatus());
            stmt.setTimestamp(4, order.getCreatedAt());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // Get the auto-generated order_id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    /**
     * Updates the status of an order (PENDING, CONFIRMED, FAILED).
     */
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status=? WHERE order_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }

    /**
     * Optional: Fetch an order by its ID.
     */
    public Order getOrder(int orderId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setSkuId(rs.getInt("sku_id"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    return order;
                }
            }
        }
        return null;
    }
}
