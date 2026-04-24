package com.lailatulcoders.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;
import com.lailatulcoders.service.InventoryService;
import com.lailatulcoders.service.OrderService;
import com.lailatulcoders.service.ProcurementService;
import com.lailatulcoders.service.SupplierService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcurementServiceImpl implements ProcurementService {

    private static final Logger logger = LoggerFactory.getLogger(ProcurementServiceImpl.class);

    private final InventoryService inventoryService;
    private final SupplierService supplierService;
    private final OrderService orderService;

    @Override
    public void processRestock(Product product) {
        try {
            logger.info("[RESTOCK PROCESS START] Product: {}", product.getId());

            if (!inventoryService.needsRestock(product)) {
                logger.info("[NO RESTOCK NEEDED] Product: {}", product.getId());
                return;
            }

            Supplier best = supplierService.getBestSupplier(product);
            int quantity = 50;

            logger.info("[RESTOCK DECISION] Product: {} | Supplier: {} | Qty: {}", product.getId(), best.getName(), quantity);

            orderService.createOrder(product, best, quantity);
            inventoryService.addStock(product, quantity);

            logger.info("[RESTOCK COMPLETED] Product: {}", product.getId());

        } catch (Exception e) {
            logger.error("[ERROR RESTOCK PROCESS] {}", e.getMessage(), e);
            throw e;
        }
    }
}
