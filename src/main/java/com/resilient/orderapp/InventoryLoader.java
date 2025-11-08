package com.resilient.orderapp;
import com.resilient.orderapp.InventoryDAO;
import com.resilient.orderapp.InventoryItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InventoryLoader {
    public static void loadInventoryFromCSV(String csvFilePath) {
        InventoryDAO inventoryDAO = new InventoryDAO();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                int skuId = Integer.parseInt(data[0].trim());
                String skuName = data[1].trim();
                int stockQty = Integer.parseInt(data[2].trim());
                double price = Double.parseDouble(data[3].trim());
                String warehouse = data[4].trim();

                InventoryItem item = new InventoryItem(skuId, skuName, stockQty, price, warehouse);
                inventoryDAO.insertInventoryItem(item);
            }

            System.out.println("Inventory loaded successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
