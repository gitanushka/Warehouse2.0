# Warehouse2.0
Smart Inventory Forecasting & Order Processing System

🏗️ Project Overview — Warehouse Order App

This project simulates a mini order management system with inventory, orders, and RabbitMQ messaging.

📦 1. inventory.csv (Data Source)

Located in: src/main/resources/
Simulates supplier stock feed.

Example:

sku_id,sku_name,stock_qty,price,warehouse
101,Wireless Mouse,150,499,WH1
102,Keyboard,100,999,WH1
103,Headphones,200,1299,WH2

🧩 2. com.resilient.orderapp.model

Contains data models (POJOs).
InventoryItem.java → represents one product record.

int skuId;
String skuName;
int stockQty;
BigDecimal price;
String warehouse;


Order.java → represents an order entry.

int orderId;
int skuId;
int quantity;
String status;  // PENDING, CONFIRMED, FAILED
Timestamp createdAt;

🗄️ 3. com.resilient.orderapp.dao

Handles Database operations (CRUD).
DBConnection.java (in util) → Creates and manages MySQL connection.
InventoryDAO.java → Inserts/updates inventory data in the DB from inventory.csv.
OrderDAO.java → Adds new orders, updates order status (CONFIRMED/FAILED).

⚙️ 4. com.resilient.orderapp.service

Implements business logic.

InventoryService.java
Reads inventory.csv from resources.
Uses InventoryDAO to insert/update DB.
Acts as the "Inventory Loader".

OrderService.java
When an order comes in (from RabbitMQ), checks stock availability.
Reduces stock if available, marks order as CONFIRMED.
Marks as FAILED if stock insufficient.

📬 5. com.resilient.orderapp.messaging

Handles RabbitMQ communication.
RabbitMQConsumer.java
Listens to orders_queue.
When a message arrives (like { "orderId":1, "skuId":101, "quantity":2 }),
→ passes it to OrderService to process.

🚀 6. com.resilient.orderapp.Main

Main entry point of the application.
Starts the app.
Calls InventoryService to load inventory from CSV.
Initializes RabbitMQ listener.
Prints logs showing progress.

🧠 Summary Flow

Load inventory.csv → Insert into MySQL (via DAO).
RabbitMQ publishes an order message → Consumer reads it.
OrderService validates stock → Updates DB accordingly






<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/60853b22-401e-44ea-af85-cdcfccaa83e3" />
