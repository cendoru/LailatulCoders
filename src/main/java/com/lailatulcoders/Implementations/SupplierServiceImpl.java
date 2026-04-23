package com.lailatulcoders.Implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lailatulcoders.DTO.Supplier;
import com.lailatulcoders.Services.SupplierService;

public class SupplierServiceImpl /*implements SupplierService*/ {

    // productId: list of suppliers
    private Map<Integer, List<Supplier>> supplierDB = new HashMap<>();

    // Optional: method to add suppliers (for testing/demo)
    public void addSupplier(int productId, Supplier supplier) {
        supplierDB.computeIfAbsent(productId, k -> new ArrayList<>()).add(supplier);
    }

    @Override
    public List<Supplier> getAllSuppliers(int productId) {
        return supplierDB.getOrDefault(productId, new ArrayList<>());
    }

    @Override
    public Supplier getBestSupplier(int productId) {
        List<Supplier> suppliers = supplierDB.get(productId);

        if (suppliers == null || suppliers.isEmpty()) {
            throw new RuntimeException("No suppliers found for product: " + productId);
        }

        Supplier bestSupplier = null;
        double bestScore = Double.MAX_VALUE;

        for (Supplier supplier : suppliers) {
            // Simple weightage formula
            double score = calculateScore(supplier);

            if (score < bestScore) {
                bestScore = score;
                bestSupplier = supplier;
            }
        }

        return bestSupplier;
    }

    // Weightage formula
    private double calculateScore(Supplier supplier) {
        double priceWeight = 0.7;
        double leadTimeWeight = 0.3;

        return (supplier.getPrice() * priceWeight) + (supplier.getLeadTime() * leadTimeWeight);
    }
}
