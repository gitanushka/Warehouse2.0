package com.resilient.orderapp;

public class InventoryItem {
    private int skuId;
    private String skuName;
    private int stockQty;
    private double price;
    private String warehouse;

    // Constructor
    public InventoryItem(int skuId, String skuName, int stockQty, double price, String warehouse) {
        this.skuId = skuId;
        this.skuName = skuName;
        this.stockQty = stockQty;
        this.price = price;
        this.warehouse = warehouse;
    }

    public InventoryItem() {

    }

    // Getters & Setters
    public int getSkuId() { return skuId; }
    public String getSkuName() { return skuName; }
    public int getStockQty() { return stockQty; }
    public double getPrice() { return price; }
    public String getWarehouse() { return warehouse; }

    // Setters
    public void setSkuId(int skuId) { this.skuId = skuId; }
    public void setSkuName(String skuName) { this.skuName = skuName; }
    public void setStockQty(int stockQty) { this.stockQty = stockQty; }
    public void setPrice(double price) { this.price = price; }
    public void setWarehouse(String warehouse) { this.warehouse = warehouse; }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "skuId=" + skuId +
                ", skuName='" + skuName + '\'' +
                ", stockQty=" + stockQty +
                ", price=" + price +
                ", warehouse='" + warehouse + '\'' +
                '}';
    }




}
