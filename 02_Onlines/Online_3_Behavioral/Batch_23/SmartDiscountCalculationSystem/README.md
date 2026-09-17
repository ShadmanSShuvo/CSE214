# Problem Statement: Smart Discount Calculation System

**Batch**: CSE23
**Source**: `Smart Discount Calculation System.pdf`
**Duration**: 30 Minutes
**Design Pattern**: **Strategy Pattern**

---

## 1. Problem Description

An e-commerce platform wants to provide customers with the best possible discount during checkout.
For every purchase, the system considers three different discount policies:
1. **Purchase Amount Discount**
2. **Customer Category Discount**
3. **Payment Method Discount**

Each policy calculates a discount percentage independently. **Discounts are not added together.**
After evaluating all applicable policies, the system applies **only the highest discount percentage** to the purchase.

### 1. Purchase Amount Discount
- For every complete ৳1,000 of purchase, customer receives 5% discount, up to a maximum of 25%:
  - Below ৳1,000: 0%
  - ৳1,000 - ৳1,999: 5%
  - ৳2,000 - ৳2,999: 10%
  - ৳3,000 - ৳3,999: 15%
  - ৳4,000 - ৳4,999: 20%
  - ৳5,000 or more: 25%

### 2. Customer Category Discount
- **REGULAR**: 5%
- **PREMIUM**: 15%

### 3. Payment Method Discount
- **CARD**: 2%
- **MFS** (e.g. bKash, Rocket): 5%
- **CASH**: 8%

### Example Calculations:
- **Example 1**:
  - Purchase Amount: ৳3,500 | Customer Type: `PREMIUM` | Payment Method: `CASH`
  - Policies evaluated: Purchase Amount (15%), Category (15%), Payment Method (8%)
  - **Applied Discount: 15%** &rarr; Payable: ৳2,975.00
- **Example 2**:
  - Purchase Amount: ৳5,500 | Customer Type: `PREMIUM` | Payment Method: `CASH`
  - Policies evaluated: Purchase Amount (25%), Category (15%), Payment Method (8%)
  - **Applied Discount: 25%** &rarr; Payable: ৳4,125.00

---

## 2. Design Pattern Justification: Strategy Pattern

- **Why Strategy Pattern?**:
  Each discount rule defines a specific discount-calculation algorithm.
- Modeling each policy as an independent strategy class implementing `DiscountPolicy` ensures:
  1. High cohesion: Each rule encapsulates its own business formula.
  2. Open/Closed Principle: New discount strategies (e.g., Seasonal Festival Discount, Referral Discount) can be plugged in without modifying the checkout calculation engine.
  3. Clean selection: The discount engine evaluates each strategy independently and selects the maximal strategy.

---

## 3. Class Diagram

```
                 +-----------------------------------------------+
                 |             SmartDiscountEngine               |
                 +-----------------------------------------------+
                 | - policies: List<DiscountPolicy>              |
                 | + registerPolicy(policy: DiscountPolicy)      |
                 | + calculateBestDiscount(PurchaseContext): ... |
                 +-----------------------------------------------+
                                         |
                                         v evaluates
                 +-----------------------------------------------+
                 |                 <<interface>>                 |
                 |                DiscountPolicy                 |
                 +-----------------------------------------------+
                 | + calculateDiscount(context: PurchaseContext) |
                 | + getPolicyName(): String                     |
                 +-----------------------------------------------+
                     ^                   ^                  ^
                     |                   |                  |
         +-----------+                   |                  +-----------+
         |                               |                              |
+------------------+           +--------------------+         +--------------------+
|  PurchaseAmount  |           |  CustomerCategory  |         |   PaymentMethod    |
|  DiscountPolicy  |           |   DiscountPolicy   |         |   DiscountPolicy   |
+------------------+           +--------------------+         +--------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE23/SmartDiscountCalculationSystem
javac *.java
java Main
```
