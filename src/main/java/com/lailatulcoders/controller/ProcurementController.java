package com.lailatulcoders.controller;
import com.lailatulcoders.model.*;
import com.lailatulcoders.service.ProcurementService;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/procurement")
@RequiredArgsConstructor
public class ProcurementController {

    private final ProcurementService procurementService;
    private final Random random = new Random();

    @PostMapping("/restock/{productId}")
    public String restock(@PathVariable Long productId) {

        Product product = new Product();
        product.setId(productId);
        Order order = new Order();

        if (product.getStockLevel() >= product.getRestockThreshold()) {
            return "No restock needed for product ID " + productId;
        }

        List<Supplier> suppliers = order.getSupplierId();

        if (suppliers == null || suppliers.isEmpty()) {
            return "No suppliers available for product ID " + productId;
        }

        Supplier supplier = suppliers.stream()
                .min(Comparator.comparingDouble(s ->
                        (s.getPrice() * 0.7) + (s.getLeadTime() * 0.3)))
                .orElseThrow();

        int quantity = random.nextInt(50) + 10;

        procurementService.processRestock(product);

        return "Restock triggered for product ID " + productId +
                " | Supplier: " + supplier.getName() +
                " | Qty: " + quantity;
    }

    @Scheduled(fixedRate = 600000)
    public void autoRestock() {

        Product product = new Product();
        Order order = new Order();
        product.setId(1L); 

        if (product.getStockLevel() >= product.getRestockThreshold()) {
            return;
        }

        List<Supplier> suppliers = order.getSupplierId();

        if (suppliers == null || suppliers.isEmpty()) {
            return;
        }

        Supplier supplier = suppliers.stream()
                .min(Comparator.comparingDouble(s ->
                        (s.getPrice() * 0.7) + (s.getLeadTime() * 0.3)))
                .orElseThrow();

        int quantity = random.nextInt(50) + 10;

        procurementService.processRestock(product);

        System.out.println("Auto-restocked product ID " + product.getId() +
                " | Supplier: " + supplier.getName() +
                " | Qty: " + quantity);

    }

}