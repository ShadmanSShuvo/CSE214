# Problem Statement: E-Commerce Payment System

**Batch**: CSE21
**Source**: `Sec A.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **Strategy Pattern**

---

## 1. Problem Description

You need to build a payment system for an e-commerce platform that supports multiple payment methods, such as credit cards, BKash, and cryptocurrency (e.g., Bitcoin). The platform should let customers choose their preferred payment method during checkout and process the payment accordingly.

The system should allow customers to easily switch between payment methods during checkout.
The owner might expect to extend new payment methods in the future.

### Expectations:
- When a customer selects a payment method, the system should use the corresponding strategy to process the payment.
- The system should easily integrate new payment methods without significant changes (Open/Closed Principle).

---

## 2. Design Pattern Justification: Strategy Pattern

- **Why Strategy?**:
  Payment algorithms vary across methods (Credit Card validation, BKash OTP/PIN verification, Blockchain wallet transfer). Encapsulating each payment method in a dedicated strategy class implementing `PaymentStrategy` allows:
  1. Dynamic switching of payment methods at runtime.
  2. Adding new payment methods (e.g., Nagad, Apple Pay) without modifying the checkout system.
  3. Adhering to the Single Responsibility Principle and Open/Closed Principle.

---

## 3. Class Diagram

```
                +---------------------------------------+
                |            CheckoutSystem             |
                +---------------------------------------+
                | - paymentStrategy: PaymentStrategy    |
                | + setPaymentStrategy(strategy)        |
                | + processCheckout(amount)             |
                +---------------------------------------+
                                    |
                                    v uses
                +---------------------------------------+
                |             <<interface>>             |
                |            PaymentStrategy            |
                +---------------------------------------+
                | + processPayment(double amount)       |
                | + getMethodName(): String             |
                +---------------------------------------+
                     ^             ^               ^
                     |             |               |
         +-----------+             |               +-----------+
         |                         |                           |
+---------------------+  +--------------------+  +-------------------------+
|  CreditCardPayment  |  |    BKashPayment    |  |      CryptoPayment      |
+---------------------+  +--------------------+  +-------------------------+
| - cardNumber        |  | - mobileNumber     |  | - walletAddress         |
| - cardHolder        |  | - pin              |  | - cryptoCurrency (BTC)  |
+---------------------+  +--------------------+  +-------------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE21/EcommercePayment
javac *.java
java Main
```
