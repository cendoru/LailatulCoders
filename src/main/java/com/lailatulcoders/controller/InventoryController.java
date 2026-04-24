package com.lailatulcoders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    public String addStock(@RequestBody Product product,
                           @RequestParam int quantity) {
        try {
            logger.info("[REQUEST ADD STOCK] Product: {} | Qty: {}", product.getId(), quantity);

            inventoryService.addStock(product, quantity);

            return "Stock added successfully";

        } catch (Exception e) {
            logger.error("[ERROR ADD STOCK CONTROLLER] {}", e.getMessage(), e);
            return "Failed to add stock: " + e.getMessage();
        }
    }

    @PostMapping("/remove")
    public String removeStock(@RequestBody Product product,
                              @RequestParam int quantity) {
        try {
            logger.info("[REQUEST REMOVE STOCK] Product: {} | Qty: {}", product.getId(), quantity);

            inventoryService.removeStock(product, quantity);

            return "Stock removed successfully";

        } catch (Exception e) {
            logger.error("[ERROR REMOVE STOCK CONTROLLER] {}", e.getMessage(), e);
            return "Failed to remove stock: " + e.getMessage();
        }
    }

    @GetMapping("/restock-check")
    public boolean needsRestock(@RequestBody Product product) {
        try {
            logger.info("[REQUEST RESTOCK CHECK] Product: {}", product.getId());

            return inventoryService.needsRestock(product);

        } catch (Exception e) {
            logger.error("[ERROR RESTOCK CHECK CONTROLLER] {}", e.getMessage(), e);
            throw e;
        }
    }
}
