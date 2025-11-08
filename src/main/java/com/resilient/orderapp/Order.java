package com.resilient.orderapp;

import java.sql.Timestamp;

public class Order {
    private int orderId;       // PK (auto-increment)
    private int skuId;         // FK → inventory.sku_id
    private int quantity;      // how many units ordered
    private String status;     // PENDING, CONFIRMED, FAILED
    private Timestamp createdAt; // order creation timestamp

    public Order() {}

    public Order(int skuId, int quantity, String status, Timestamp createdAt) {
        this.skuId = skuId;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", skuId=" + skuId +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
