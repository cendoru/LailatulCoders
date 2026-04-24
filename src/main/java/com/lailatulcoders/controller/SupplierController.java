package com.lailatulcoders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;
import com.lailatulcoders.service.SupplierService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public String addSupplier(@RequestBody Product product,
                              @RequestParam String name,
                              @RequestParam double price,
                              @RequestParam int leadTime) {
        try {
            Supplier supplier = new Supplier();
            supplier.setName(name);
            supplier.setPrice(price);
            supplier.setLeadTime(leadTime);

            supplierService.addSupplier(product, supplier);

            logger.info("[SUPPLIER ADDED] Product: {} | Supplier: {}", product.getId(), name);

            return "Supplier added";

        } catch (Exception e) {
            logger.error("[ERROR ADD SUPPLIER] {}", e.getMessage(), e);
            return "Failed to add supplier";
        }
    }

    @GetMapping("/{productId}")
    public List<Supplier> getAllSuppliers(@PathVariable int productId) {
        try {
            Product product = new Product();
            product.setId(productId);

            return supplierService.getAllSuppliers(product);

        } catch (Exception e) {
            logger.error("[ERROR GET SUPPLIERS] {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{productId}/best")
    public Supplier getBestSupplier(@PathVariable int productId) {
        try {
            Product product = new Product();
            product.setId(productId);

            return supplierService.getBestSupplier(product);

        } catch (Exception e) {
            logger.error("[ERROR BEST SUPPLIER CONTROLLER] {}", e.getMessage(), e);
            throw e;
        }
    }
}
