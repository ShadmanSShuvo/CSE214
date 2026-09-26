# Offline 1: FoodFlow — Restaurant Ordering & Menu Engine

**Course:** BUET CSE 214: Software Engineering & Object-Oriented Design Patterns Sessional
**Assignment:** Offline 1 (Creational Design Patterns)
**Student ID:** `2305025`
**Core Patterns:** **Builder Pattern**, **Factory Method Pattern**, **Singleton Pattern**

---

## 🍽️ System Overview

**FoodFlow** is an enterprise restaurant ordering and automated receipt generation platform. The system is designed to handle complex customizable orders with varied item sizes, optional addons, multi-mode delivery (Store Pickup vs. Home Delivery), scheduled delivery times, payment methods, promotional coupon codes, and loyalty reward redemption.

---

## 🏗️ Architectural Design & Creational Patterns

### 1. The Builder Pattern (`Order` & `OrderItem`)
- **Motivation:** In a flexible food ordering system, an `Order` consists of mandatory identification attributes (`orderId`, `customerName`, `phone`, `items`) and up to 10 optional configurations (`deliveryType`, `deliveryAddress`, `paymentMethod`, `scheduledTime`, `couponCode`, `giftWrap`, `cutleryRequired`, `loyaltyPointsToRedeem`, `rushOrder`, `specialInstructions`). Constructing this via standard constructors leads to the **telescoping constructor anti-pattern** and fragile positional arguments.
- **Implementation Highlights:**
  - `Order.builder(orderId, customerName, phone, items)` captures required invariants at the entry point.
  - Fluent chained setter methods (`.deliveryType(...)`, `.couponCode(...)`, etc.) configure optional properties.
  - The `.build()` method encapsulates strict business validation:
    - Verifies non-blank customer identifiers.
    - Requires non-empty delivery addresses when `deliveryType == DeliveryType.DELIVERY`.
    - Clamps loyalty points to non-negative values.
    - Creates an unmodifiable defensive copy of the `OrderItem` collection.

```
                    +--------------------+
                    |       Order        |
                    +--------------------+
                    | - orderId: String  |
                    | - items: List      |
                    | - address: String  |
                    +--------------------+
                              ^
                              | builds
                    +--------------------+
                    |   Order.Builder    |
                    +--------------------+
                    | +deliveryType()    |
                    | +couponCode()      |
                    | +build(): Order    |
                    +--------------------+
```

### 2. The Singleton Pattern (`MenuCatalog`)
- **Motivation:** Menu pricing, base items, and size adjustments must remain consistent and centralized across the entire application runtime.
- **Implementation:** Centralized in-memory catalog loading from `data/menu.csv` ensuring thread-safe access and zero redundant disk I/O.

### 3. Factory & IO Separation
- **`CsvMenuLoader`**: Parses CSV records into domain `MenuItem` instances with defensive validation.
- **`ReceiptWriter` & `ReceiptService`**: Formats structured, itemized receipts displaying base totals, size modifiers, delivery surcharges, applied discounts, and net payable amounts.

---

## 📁 Subdirectory Map

| Subdirectory | Description |
| :--- | :--- |
| **[`2305025/`](2305025/)** | **Official evaluated submission package** containing pure Java implementation, unit test harness (`TestHarness.java`), spec, and CSV data. |
| **[`Starter_Template/`](Starter_Template/)** | The baseline unrefactored skeleton provided by course instructors. |

---

## 🚀 Compilation & Execution

### Running the Official Submission CLI
```bash
cd 2305025
javac src/*.java src/*/*.java
java -cp src Main
```

### Running the Automated Test Harness
```bash
cd 2305025
javac -cp src TestHarness.java
java -cp .:src TestHarness
```
