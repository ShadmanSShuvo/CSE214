# Batch 21 Online 2 (B1) - Delivery Discounts (Decorator Pattern)

## Problem Statement
A delivery service offers discounts to its customers based on certain conditions. When a customer makes a purchase, they may be eligible for one or more of the following discounts:
1. **Loyalty Discount**: If the customer is a premium member, they receive a **10% discount** on their purchase.
2. **Seasonal Discount**: During special promotional seasons, an additional **flat discount of 100 units** is applied to the purchase price.
3. **High-Value Purchase Discount**: If the purchase amount exceeds 10,000 units, an **additional 2% discount** is applied.

**Task**: Design a solution that calculates the final price after applying all applicable discounts. Each discount can be applied independently, and multiple discounts may be combined in any permutation if the customer qualifies.

Base purchase interface provided:
```java
interface Purchase {
    double calculatePrice();
}
```

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern**

### Why Decorator?
- **Combinatorial Explosion of Subclasses**: With 3 independent discounts, creating subclasses for every possible combination ($2^3 = 8$ classes) leads to class explosion.
- **Dynamic Responsibility Attachment**: Discounts can be dynamically added, removed, or stacked onto a base purchase at runtime based on flags (`isPremiumMember`, `isSeasonalPromotion`, `basePrice > 10000`).
- **Open/Closed Principle**: New promotional discounts (e.g., student discount, first-order discount) can be introduced as new decorators without modifying existing discount code.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `Purchase` | Interface declaring `calculatePrice()`. |
| **Concrete Component** | `BasePurchase` | Represents base purchase amount without any discount. |
| **Decorator** | `DiscountDecorator` | Abstract decorator holding a reference to a `Purchase` wrappee. |
| **Concrete Decorators** | `LoyaltyDiscount`, `SeasonalDiscount`, `HighValueDiscount` | Modify calculated price by applying percentage or flat discounts. |
| **Client** | `B1` | Dynamically wraps purchase with qualifying discounts. |

---

## Class Architecture

```
                    <<interface>>
                      Purchase
               +calculatePrice(): double
                         ^
                         |
        +----------------+----------------+
        |                                 |
   BasePurchase                   DiscountDecorator
   -price: double                 -wrappedPurchase: Purchase
   +calculatePrice(): double      +calculatePrice(): double
                                          ^
                                          |
        +------------------+--------------+------------------+
        |                                 |                  |
 LoyaltyDiscount                   SeasonalDiscount   HighValueDiscount
 (10% off: price * 0.9)            (flat 100 off)     (2% off: price * 0.98)
```

---

## Solution Walkthrough

1. **Base Component**:
   ```java
   class BasePurchase implements Purchase {
       private double price;
       public BasePurchase(double price) { this.price = price; }
       @Override public double calculatePrice() { return price; }
   }
   ```
2. **Abstract Decorator**:
   ```java
   abstract class DiscountDecorator implements Purchase {
       protected Purchase wrappedPurchase;
       public DiscountDecorator(Purchase purchase) { this.wrappedPurchase = purchase; }
       @Override public double calculatePrice() { return wrappedPurchase.calculatePrice(); }
   }
   ```
3. **Concrete Decorators**:
   - `LoyaltyDiscount`: `wrappedPurchase.calculatePrice() * 0.90`
   - `SeasonalDiscount`: `wrappedPurchase.calculatePrice() - 100`
   - `HighValueDiscount`: `wrappedPurchase.calculatePrice() * 0.98`
4. **Conditional Stacking in Driver**:
   ```java
   Purchase purchase = new BasePurchase(12000);
   if (isPremiumMember) purchase = new LoyaltyDiscount(purchase);
   if (isSeasonalPromotion) purchase = new SeasonalDiscount(purchase);
   if (basePrice > 10000) purchase = new HighValueDiscount(purchase);
   ```

---

## How to Compile & Run

```bash
cd Batch_21/B1_DeliveryDiscountDecorator
javac *.java
java B1
```

### Output
```
Final price after all discounts: 10486.0
```
*(Calculation: 12000 * 0.90 = 10800; 10800 - 100 = 10700; 10700 * 0.98 = 10486.0)*
