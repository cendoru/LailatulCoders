package com.lailatulcoders.controller;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;

import com.lailatulcoders.service.InventoryService;
import com.lailatulcoders.service.ProcurementService;
import com.lailatulcoders.service.SupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/procurement")
@RequiredArgsConstructor
public class ProcurementController {

    private final ProcurementService procurementService;
    private final InventoryService inventoryService;
    private final SupplierService supplierService;

    private final Random random = new Random();

    @PostMapping("/restock/{productId}")
    public String restock(@PathVariable Long productId) {

        Product product = new Product();
        product.setId(productId.intValue());

        if (!inventoryService.needsRestock(product)) {
            return "No restock needed for product ID " + productId;
        }

        List<Supplier> suppliers = supplierService.getAllSuppliers(product);

        if (suppliers == null || suppliers.isEmpty()) {
            return "No suppliers available for product ID " + productId;
        }

        Supplier supplier = supplierService.getBestSupplier(product);

        int quantity = random.nextInt(50) + 10;

        procurementService.processRestock(product);

        return "Restock triggered for product ID " + productId +
                " | Supplier: " + supplier.getName() +
                " | Qty: " + quantity;
    }

    @Scheduled(fixedRate = 600000)
    public void autoRestock() {

        // NOTE: replace with DB fetch later
        Product product = new Product();
        product.setId(1);

        if (!inventoryService.needsRestock(product)) {
            return;
        }

        List<Supplier> suppliers = supplierService.getAllSuppliers(product);

        if (suppliers == null || suppliers.isEmpty()) {
            return;
        }

        Supplier supplier = supplierService.getBestSupplier(product);

        int quantity = random.nextInt(50) + 10;

        procurementService.processRestock(product);

        System.out.println("Auto-restocked product ID " + product.getId() +
                " | Supplier: " + supplier.getName() +
                " | Qty: " + quantity);
    }
}