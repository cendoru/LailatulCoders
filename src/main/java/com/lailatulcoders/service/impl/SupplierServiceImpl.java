package com.lailatulcoders.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;
import com.lailatulcoders.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    // productId: list of suppliers
    private Map<Integer, List<Supplier>> supplierDB = new HashMap<>();

    // Optional: method to add suppliers (for testing/demo)
    @Override
    public void addSupplier(Product product, Supplier supplier) {
        supplierDB.computeIfAbsent(product.getId(), k -> new ArrayList<>()).add(supplier);
        
        logger.info("[SUPPLIER ADDED] Product: {} | Supplier: {}", product.getId(), supplier.getName());
    }

    @Override
    public List<Supplier> getAllSuppliers(Product product) {
        List<Supplier> list = supplierDB.getOrDefault(product.getId(), new ArrayList<>());
    
        logger.info("[GET SUPPLIERS] Product: {} | Count: {}", product.getId(), list.size());
    
        return list;
    }

    @Override
    public Supplier getBestSupplier(Product product) {
        try {
            List<Supplier> suppliers = supplierDB.get(product.getId());
    
            if (suppliers == null || suppliers.isEmpty()) {
                throw new RuntimeException("No suppliers found");
            }
    
            Supplier best = suppliers.stream()
                    .min((a, b) -> Double.compare(calculateScore(a), calculateScore(b)))
                    .orElse(null);
    
            logger.info("[BEST SUPPLIER] Product: {} | Supplier: {}", product.getId(), best.getName());
    
            return best;
    
        } catch (Exception e) {
            logger.error("[ERROR BEST SUPPLIER] {}", e.getMessage(), e);
            throw e;
        }
    }

    // Weightage formula
    private double calculateScore(Supplier supplier) {
        double priceWeight = 0.7;
        double leadTimeWeight = 0.3;

        return (supplier.getPrice() * priceWeight) + (supplier.getLeadTime() * leadTimeWeight);
    }
}
