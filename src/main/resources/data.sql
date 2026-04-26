DELETE FROM product_supplier;
DELETE FROM products;
DELETE FROM suppliers;

-- products(20)
INSERT INTO products (product_id, product_name, stock_level, restock_threshold) VALUES
(1, 'Organic Banana', 150, 50),
(2, 'Whole Wheat Bread',  75, 30),
(3, 'Almond Milk', 40, 15),
(4, 'Free Range Eggs', 120, 40),
(5, 'Greek Yogurt', NULL, 20),
(6, 'Rolled Oats', 200, 30),
(7, 'Raw Honey', 100, 30),
(8, 'Coffee Beans', 200, 60),
(9, 'Spinach', 130, 20),
(10, 'Chicken Breast', NULL, 80),
(11, 'Brown Rice', 150, 25),
(12, 'Avocado', 140, 60),
(13, 'Cheddar Cheese', 200, 35),
(14, 'Tomato Sauce', 100, 20),
(15, 'Pasta', 200, 30),
(16, 'Orange Juice', 0, 20),
(17, 'Tofu', 80, 20),
(18, 'Frozen Berries', 40, 30),
(19, 'Peanut Butter', 65, 20),
(20, 'Quinoa', 25, 30);

-- suppliers (12)
INSERT INTO suppliers (supplier_id, supplier_name, lead_time, supplier_rating) VALUES
(1, 'FreshFarm Co.', 15.5, 4.8),
(2, 'EcoDistributors', 19.5, 4.5),
(3, 'Golden Harvest', 26.0, 4.7),
(4, 'Nature''s Best', 10.5, 4.2),
(5, 'Pure源Foods', 20.4, 4.1),
(6, 'UrbanFresh', 13, 4.9),
(7, 'DairyPure', 9.5, 4.6),
(8, 'GrainMaster',6.0, 4.4),
(9, 'FrozenDirect',33.5, 4.3),
(10, 'OrganicAsia', 18.0, 4.7),
(11, 'BeverageHub', NULL, 4.1),
(12, 'MeatMaster', 5.0, NULL);


-- Product 1: Banana ( store price RM2.99 )
INSERT INTO product_supplier(product_id, supplier_id, supplier_price) VALUES 
(1, 1, 2.50),   -- FreshFarm
(1, 2, 2.80),   -- EcoDistributors  
(1, 5, 2.60),   -- Pure源Foods
(2, 1, 3.00),   -- FreshFarm
(2, 3, 3.20),   -- Golden Harvest
(2, 6, 2.90),   -- UrbanFresh (best price)
(3, 2, 4.50),   -- EcoDistributors
(3, 4, 4.70),   -- Nature's Best
(3, 5, 4.40),   -- Pure源Foods (best price)
(4, 1, 5.50),   -- FreshFarm
(4, 6, 5.30),   -- UrbanFresh (best price)
(4, 7, 5.70),   -- DairyPure
(5, 2, 1.10),   -- EcoDistributors (best price)
(5, 3, 1.20),   -- Golden Harvest
(5, 7, NULL),   -- DairyPure (NULL price for error testing)
(6, 8, 3.80),   -- GrainMaster
(6, 9, 4.00),   -- FrozenDirect 
(7, 3, 8.50),   -- Golden Harvest
(7, 4, 8.70),   -- Nature's Best
(7, 6, 8.20),   -- UrbanFresh (best price)
(8, 4, 12.00),  -- Nature's Best
(8, 5, 12.50),  -- Pure源Foods
(8, 11, 11.80), -- BeverageHub (best price)
(9, 1, 2.20),   -- FreshFarm (best price)
(9, 10, 2.40),  -- OrganicAsia 
(10, 6, 14.00), -- UrbanFresh
(10, 12, 13.50), -- MeatMaster (best price)
(11, 2, 4.00),  -- EcoDistributors
(11, 8, 4.20),  -- GrainMaster
(11, 10, 3.90), -- OrganicAsia (best price) 
(12, 1, 1.80),  -- FreshFarm (best price)
(12, 5, 1.90),  -- Pure源Foods
(13, 4, 6.50),  -- Nature's Best
(13, 6, 6.30),  -- UrbanFresh (best price)
(13, 7, 6.70),  -- DairyPure
(14, 3, 2.00),  -- Golden Harvest
(14, 8, 2.10),  -- GrainMaster
(14, 9, 1.90),  -- FrozenDirect (best price)
(15, 2, 1.30),  -- EcoDistributors
(15, 4, 1.40),  -- Nature's Best
(15, 8, 1.20),  -- GrainMaster (best price)
(15, 11, 1.35), -- BeverageHub
(16, 5, 3.80),  -- Pure源Foods
(16, 11, 3.60), -- BeverageHub (best price)
(17, 9, 2.80),  -- FrozenDirect
(17, 10, 2.70), -- OrganicAsia (best price)
(17, 12, 2.90), -- MeatMaster
(18, 9, 5.00),  -- FrozenDirect (best price)
(18, 12, 5.30), -- MeatMaster
(19, 3, 4.00),  -- Golden Harvest (best price)
(19, 5, 4.20),  -- Pure源Foods
(19, 8, 4.10),  -- GrainMaster
(20, 8, 7.50),  -- GrainMaster (best price)
(20, 10, 7.80), -- OrganicAsia
(20, 11, 7.70); -- BeverageHub