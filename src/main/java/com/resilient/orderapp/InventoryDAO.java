package com.resilient.orderapp;
import com.resilient.orderapp.InventoryItem;
import com.resilient.orderapp.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class InventoryDAO {

    public void insertInventoryItem(InventoryItem item) {
        String sql = "INSERT INTO inventory (sku_id, sku_name, stock_qty, price, warehouse) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getSkuId());
            stmt.setString(2, item.getSkuName());
            stmt.setInt(3, item.getStockQty());
            stmt.setDouble(4, item.getPrice());
            stmt.setString(5, item.getWarehouse());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Check available stock for a SKU
    public int getStock(int skuId) {
        String sql = "SELECT stock_qty FROM inventory WHERE sku_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, skuId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock_qty");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // SKU not found or error
    }

    // ✅ Update stock after confirming order
    public boolean updateStock(int skuId, int quantity) {
        String sql = "UPDATE inventory SET stock_qty = ? WHERE sku_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setInt(2, skuId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // success if stock was reduced

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Fetch item details (optional)
    public InventoryItem getItem(int skuId) {
        String sql = "SELECT * FROM inventory WHERE sku_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, skuId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    InventoryItem item = new InventoryItem(){};
                    item.setSkuId(rs.getInt("sku_id"));
                    item.setSkuName(rs.getString("sku_name"));
                    item.setStockQty(rs.getInt("stock_qty"));
                    item.setPrice(rs.getDouble("price"));
                    item.setWarehouse(rs.getString("warehouse"));
                    return item;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}