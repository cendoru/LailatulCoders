package com.lailatulcoders.Implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lailatulcoders.DTO.InventoryLog;
import com.lailatulcoders.DTO.Product;
import com.lailatulcoders.Services.InventoryService;

public class InventoryServiceImpl implements InventoryService {

    private Map<Integer, Product> productDB = new HashMap<>();

    @Override
    public void addStock(Product product, int quantity) {
        if (product == null) {
            throw new RuntimeException("Product cannot be null");
        }

        product.setStockLevel(product.getStockLevel() + quantity);
        logAction(product.getId(), quantity);
    }

    @Override
    public void removeStock(Product product, int quantity) {
        if (product == null) {
            throw new RuntimeException("Product cannot be null");
        }

        if (product.getStockLevel() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStockLevel(product.getStockLevel() - quantity);
        logAction(product.getId(), -quantity);
    }

    @Override
    public boolean needsRestock(Product product) {
        if (product == null) {
            throw new RuntimeException("Product cannot be null");
        }

        return product.getStockLevel() <= product.getRestockThreshold();
    }
}