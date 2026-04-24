package com.lailatulcoders.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lailatulcoders.model.Order;
import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;
import com.lailatulcoders.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final Map<Integer, Order> orderDB = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger();

    @Override
    public Order createOrder(Product product, Supplier supplier, int quantity) {
        try {
            if (product == null || supplier == null) {
                throw new RuntimeException("Product or Supplier cannot be null");
            }

            Order order = new Order();
            order.setId(idCounter.incrementAndGet());
            order.setProductId(product.getId());
            order.setSupplierId(supplier.getId());
            order.setQuantity(quantity);
            order.setStatus("CREATED");

            orderDB.put(order.getId(), order);

            logger.info("[ORDER CREATED] ID: {} | Product: {} | Supplier: {} | Qty: {}", order.getId(), product.getId(), supplier.getId(), quantity);

            return order;

        } catch (Exception e) {
            logger.error("[ERROR CREATE ORDER] {}", e.getMessage(), e);
            throw e;
        }
    }
}
