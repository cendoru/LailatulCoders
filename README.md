# 📦 InvAgent

## 📋 Overview

**InvAgent** is a REST API-based Inventory Management System built with Spring Boot. It provides comprehensive inventory control, supplier management, and automated procurement capabilities for small to medium enterprises (SMEs).

Built for **UMHackathon 2026**, this system demonstrates robust inventory operations with scheduled auto-restock functionality and clean RESTful API design.

---

## 🚀 Features

### Inventory Management

* Add stock to products
* Remove stock from products
* Check reorder status against thresholds
* Automatic stock level tracking

### Supplier Management

* Retrieve all suppliers for a product
* Get best supplier based on rating and lead time
* Supplier-product price mapping

### Procurement

* Manual restock triggering
* Scheduled auto-restock every 10 minutes
* Random quantity generation (10–59 units per restock)
* Supplier selection optimization

### Logging & Monitoring

* Comprehensive request/response logging
* Error tracking with stack traces
* Audit trail for all inventory changes

---

## 🏗️ Technology Stack

| Technology                | Version | Purpose                   |
| ------------------------- | ------- | ------------------------- |
| Java                      | 17      | Core language             |
| Spring Boot               | 3.x     | Application framework     |
| Spring Web                | -       | REST API endpoints        |
| Spring Scheduling         | -       | Auto-restock scheduler    |
| Jakarta Persistence (JPA) | -       | ORM for database entities |
| SLF4J + Logback           | -       | Logging framework         |
| Maven                     | -       | Build automation          |

---

## 📁 Project Structure

```
com.lailatulcoders/
├── controller/
│   ├── InventoryController.java
│   ├── SupplierController.java
│   └── ProcurementController.java
├── model/
│   ├── Product.java
│   ├── Supplier.java
│   ├── ProductSupplier.java
│   ├── Order.java
│   └── InventoryLog.java
└── service/
    ├── InventoryService.java
    ├── SupplierService.java
    └── ProcurementService.java
```

---

## 🔧 API Endpoints

### Inventory Controller (`/inventory`)

| Method | Endpoint                   | Description                    |
| ------ | -------------------------- | ------------------------------ |
| POST   | `/inventory/add`           | Add stock to a product         |
| POST   | `/inventory/remove`        | Remove stock from a product    |
| GET    | `/inventory/restock-check` | Check if product needs restock |

### Supplier Controller (`/suppliers`)

| Method | Endpoint                      | Description       |
| ------ | ----------------------------- | ----------------- |
| GET    | `/suppliers/{productId}`      | Get all suppliers |
| GET    | `/suppliers/{productId}/best` | Get best supplier |

### Procurement Controller (`/procurement`)

| Method    | Endpoint                           | Description           |
| --------- | ---------------------------------- | --------------------- |
| POST      | `/procurement/restock/{productId}` | Trigger restock       |
| Scheduled | `autoRestock()`                    | Runs every 10 minutes |

---

## 🚀 Getting Started

### Prerequisites

* Java 17+
* Maven 3.6+
* IDE (IntelliJ / Eclipse / VS Code)

---

## ⚠️ Known Limitations

* `needsRestock()` uses incorrect HTTP method
* Auto-restock only processes product ID 1
* No persistence layer shown
* Random restock quantity may be inconsistent

---

## 🔮 Future Enhancements

* AI-powered procurement recommendations
* Multi-warehouse support
* Demand forecasting
* Approval workflows (WhatsApp / Email)
* Dashboard UI
* JWT authentication
* Docker support

---

## 👥 Team

**LailatulCoders**
UMHackathon 2026
