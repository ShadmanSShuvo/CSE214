# Batch 23 Online 2 (A1) - Gift Shop Pricing & Multi-Region Delivery

## Problem Statement
A gift shop sells gift items such as showpieces, decorative ornaments, souvenirs, etc. Each gift item has a description and a base price.
- **Gift Wrapping**: Customers may optionally request that a purchased gift be wrapped, which adds **\$2** to the item's price.
- **Home Delivery**: Customers may also request home delivery.

### Delivery Regions & Pricing Rules
1. **Local Delivery**:
   - Charge: **\$1 per mile** from the shop to the destination.
   - Estimated delivery time: **1 week** (Standard).
2. **National Delivery**:
   - Charge: **\$1 per mile + fixed surcharge of \$20**.
   - Estimated delivery time: **1–2 weeks** (Standard).
3. **International Delivery**:
   - Charge: **Fixed surcharge of \$500**.
   - Estimated delivery time: **2–3 weeks** (Standard).

### Delivery Modes
Customers can choose one of the following delivery modes for any region:
- **Express Delivery**: Adds **\$10** to the delivery charge.
  - Delivery time: **2 days** for Local & National; **1 week** for International.
- **Priority Delivery**: Adds **\$25** to the delivery charge.
  - Delivery time: **1 day** for Local & National; **5 days** for International.

### Extensibility Requirement
The shop expects to introduce additional delivery regions and delivery modes in the future. The design should support extending both independently without requiring significant modifications to existing code.

---

## Test Cases from Specification

### Case 1
Decorative vase (\$40) sent 10 miles away within local city with gift wrapping:
- Total Cost: \$40 + \$2 + (10 × \$1) = **\$52**
- Estimated Delivery Time: **1 week**

### Case 2
Wooden souvenir (\$60) sent 50 miles away within country with gift wrapping and Express Delivery:
- Total Cost: \$60 + \$2 + (50 × \$1 + \$20) + \$10 = **\$142**
- Estimated Delivery Time: **2 days**

### Case 3
Crystal showpiece (\$150) shipped internationally using Priority Delivery:
- Total Cost: \$150 + \$500 + \$25 = **\$675**
- Estimated Delivery Time: **5 days**

---

## Design Pattern Analysis

### Patterns Applied: **Decorator Pattern + Bridge / Strategy Pattern**

### Why Decorator?
- Wrapping and Home Delivery are optional, stackable add-ons on top of any base gift item.
- Base interface `Gift` exposes `getPrice()`, `getDesc()`, and `getDeliveryTime()`.
- `GiftWrapDecorator` dynamically adds \$2.
- `HomeDeliveryDecorator` dynamically adds the computed delivery cost and returns the estimated delivery time.

### Why Bridge / Strategy for Delivery?
- **Two Independent Dimensions**:
  1. **Delivery Region** (`LocalRegion`, `NationalRegion`, `InternationalRegion`) calculates base charge and base duration.
  2. **Delivery Mode** (`StandardMode`, `ExpressMode`, `PriorityMode`) calculates mode surcharge and accelerated timeframe.
- Both dimensions can be extended independently without changing `HomeDeliveryDecorator` or existing regions/modes.

---

## Class Architecture

```
                 <<interface>>
                     Gift
          +getPrice(): double
          +getDesc(): String
          +getDeliveryTime(): String
                   ^
                   |
     +-------------+-------------+
     |                           |
  GiftItem                 GiftDecorator
  (Base Component)         -wrappee: Gift
                                 ^
                                 |
                 +---------------+---------------+
                 |                               |
        GiftWrapDecorator               HomeDeliveryDecorator
        (Adds $2)                       -region: DeliveryRegion
                                        -mode: DeliveryMode
                                        -distanceMiles: double
```

---

## Solution Walkthrough

1. **Gift Interface & Leaf**:
   `GiftItem` stores base price and description.
2. **Wrapping Decorator**:
   `GiftWrapDecorator` adds \$2.00 to `wrappee.getPrice()` and appends `"(Gift Wrapped)"` to the description.
3. **Delivery Decorator with Bridge**:
   `HomeDeliveryDecorator` calculates shipping cost via `region.calculateCost(distance) + mode.getSurcharge()` and resolves delivery time via `mode.getTime(region)`.
4. **Independent Delivery Dimension Classes**:
   - `DeliveryRegion`: `LocalRegion`, `NationalRegion`, `InternationalRegion`.
   - `DeliveryMode`: `StandardMode`, `ExpressMode`, `PriorityMode`.

---

## How to Compile & Run

```bash
cd Batch_23/A1_GiftShop
javac *.java
java GiftShop
```

### Verified Output
```
=== CASE 1 ===
Item: Decorative Vase (Gift Wrapped)
Total Cost: $52.00
Estimated Delivery Time: 1 week

=== CASE 2 ===
Item: Wooden Souvenir (Gift Wrapped)
Total Cost: $142.00
Estimated Delivery Time: 2 days

=== CASE 3 ===
Item: Crystal Showpiece
Total Cost: $675.00
Estimated Delivery Time: 5 days
```
