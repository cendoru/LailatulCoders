package com.lailatulcoders.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lailatulcoders.model.AgentState;
import com.lailatulcoders.model.PlanResponse;
import com.lailatulcoders.model.Product;
import com.lailatulcoders.model.Supplier;
import com.lailatulcoders.repository.ProductRepository;

@Service
public class ToolService {
    
    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final InventoryService inventoryService;

    public ToolService(ProductRepository productRepository, SupplierService supplierService, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.supplierService = supplierService;
        this.inventoryService = inventoryService;
    }

    public Map<String, Object> execute(String action, PlanResponse input, AgentState state) {

        switch (action) {

            case "find_product" -> {
                String name = input.product != null ? input.product : state.userInput;
                List<Product> matches = productRepository.searchByName(name);

                if (matches.isEmpty()) {
                    return Map.of("error", "No product found");
                }

                Product p = matches.get(0);

                state.productId = p.getId();
                state.productName = p.getName();

                return Map.of("product_id", p.getId(), "product_name", p.getName());
            }

            case "get_suppliers" -> {
                Integer id = input.product_id != null ? input.product_id : state.productId;

                if (id == null) {
                    return Map.of("error", "Missing product_id");
                }
                List<Supplier> suppliers = supplierService.getAllSuppliers(id);
                Supplier best = supplierService.getBestSupplier(id);

                return Map.of("suppliers", suppliers.stream().map(s -> Map.of("id",s.getId(), "name", s.getName(), "rating", s.getRating())).toList());
            }

            case "check_inventory" -> {
                Integer id = input.product_id != null ? input.product_id : state.productId;

                if (id == null) {
                    return Map.of("error", "Missing product_id");
                }
                Product p = productRepository.findById(id).orElse(null);

                if (p == null) {
                    return Map.of("error", "Product not found");
                }
                boolean restock = inventoryService.needsRestock(p);

                return Map.of("needs_restock", restock);
            }
        }

        return Map.of("error", "Unknown action");
    }
}
