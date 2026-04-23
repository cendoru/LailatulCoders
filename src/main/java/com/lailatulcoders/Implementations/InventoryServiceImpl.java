package com.lailatulcoders.Implementations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lailatulcoders.DTO.InventoryLog;
import com.lailatulcoders.DTO.Order;
import com.lailatulcoders.DTO.Product;
import com.lailatulcoders.Services.InventoryService;

public class InventoryServiceImpl /*implements InventoryService*/ {
    private Map<Integer, Product> productDB = new HashMap<>();
    private Map<Integer, Supplier> supplierDB = new HashMap<>();
    private Map<Integer, Order> orderDB = new HashMap<>();
    private List<InventoryLog> logs = new ArrayList<>();

    private int orderCounter = 1;

    public void addProduct(Product product) {
        productDB.put(product.getId(), product);
    }

    public Product getProduct(int id) {
        return productDB.get(id);
    }

    public void addSupplier(Supplier supplier) {
        supplierDB.put(supplier.getId(), supplier);
    }

    public void updateStock(int productId, int change, boolean allowAutoOrder) {
    Product product = productDB.get(productId);

    if (product == null) {
        throw new RuntimeException("Product not found");
    }

    int newStock = product.getStockLevel() + change;

    if (newStock < 0) {
        throw new RuntimeException("Insufficient stock");
    }

    product.setStockLevel(newStock);

    InventoryLog log = new InventoryLog();
    log.setProductId(productId);
    log.setChange(change);
    log.setTimestamp((int) (System.currentTimeMillis() / 1000));

    logs.add(log); // ✅ FIXED

    if (allowAutoOrder && newStock < product.getRestockThreshold()) {
        autoCreateOrder(product);
    }

    private void autoCreateOrder(Product product) {
        Supplier bestSupplier = chooseBestSupplier();

        if (bestSupplier == null) {
            System.out.println("No supplier available");
            return;
        }

        Order order = new Order();
        order.setId(orderCounter++);
        order.setProductId(product.getId());
        order.setSupplierId(bestSupplier.getId());
        order.setQuantity(product.getRestockThreshold() * 2); // simple strategy
        order.setStatus("PENDING");

        orderDB.put(order.getId(), order);

        System.out.println("Auto Order Created: Product " + product.getName());
    }

    private Supplier chooseBestSupplier() {
        return supplierDB.values()
                .stream()
                .min(Comparator.comparingDouble(Supplier::getPrice))
                .orElse(null);
    }

    public void receiveOrder(int orderId) {
        Order order = orderDB.get(orderId);

        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Order already processed");
        }

        updateStock(order.getProductId(), order.getQuantity(), false); // ✅ FIXED

        order.setStatus("RECEIVED");
    }

    public List<InventoryLog> getLogs() {
        return logs;
    }
}
