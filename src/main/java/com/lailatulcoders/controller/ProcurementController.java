package com.lailatulcoders.controller;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;

import com.lailatulcoders.service.InventoryService;
import com.lailatulcoders.service.ProcurementService;
import com.lailatulcoders.service.SupplierService;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/procurement")
@RequiredArgsConstructor
public class ProcurementController {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementController.class);

    private final ProcurementService procurementService;
    private final InventoryService inventoryService;
    private final SupplierService supplierService;

    private final Random random = new Random();

    @PostMapping("/restock/{productId}")
    public String restock(@PathVariable Long productId) {
        try {
            logger.info("[REQUEST RESTOCK] Product: {}", productId);

            Product product = new Product();
            product.setId(productId.intValue());

            if (!inventoryService.needsRestock(product)) {
                logger.info("[NO RESTOCK NEEDED] Product: {}", productId);
                return "No restock needed for product ID " + productId;
            }

            List<Supplier> suppliers = supplierService.getAllSuppliers(product);

            if (suppliers == null || suppliers.isEmpty()) {
                logger.warn("[NO SUPPLIERS] Product: {}", productId);
                return "No suppliers available for product ID " + productId;
            }

            Supplier supplier = supplierService.getBestSupplier(product);
            int quantity = random.nextInt(50) + 10;

            procurementService.processRestock(product);

            logger.info("[RESTOCK SUCCESS] Product: {} | Supplier: {} | Qty: {}",
                    productId, supplier.getName(), quantity);

            return "Restock triggered for product ID " + productId +
                    " | Supplier: " + supplier.getName() +
                    " | Qty: " + quantity;

        } catch (Exception e) {
            logger.error("[ERROR RESTOCK CONTROLLER] {}", e.getMessage(), e);
            return "Restock failed: " + e.getMessage();
        }
    }

    @Scheduled(fixedRate = 600000)
    public void autoRestock() {
        try {
            Product product = new Product();
            product.setId(1);

            if (!inventoryService.needsRestock(product)) {
                return;
            }

            List<Supplier> suppliers = supplierService.getAllSuppliers(product);

            if (suppliers == null || suppliers.isEmpty()) {
                logger.warn("[AUTO RESTOCK NO SUPPLIER]");
                return;
            }

            Supplier supplier = supplierService.getBestSupplier(product);
            int quantity = random.nextInt(50) + 10;

            procurementService.processRestock(product);

            logger.info("[AUTO RESTOCK] Product: {} | Supplier: {} | Qty: {}",
                    product.getId(), supplier.getName(), quantity);

        } catch (Exception e) {
            logger.error("[ERROR AUTO RESTOCK] {}", e.getMessage(), e);
        }
    }
}
