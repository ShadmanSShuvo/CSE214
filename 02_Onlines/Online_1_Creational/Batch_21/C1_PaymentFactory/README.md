# Batch 21 - Section C1: E-commerce Payment Processing

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Factory Method Pattern
- **Language:** Java

---

## 1. Problem Statement

In an e-commerce application, different payment methods are accepted:
- **Credit Card**
- **PayPal**
- **Cryptocurrency (Bitcoin)**

As the business expands, new payment methods may be introduced and existing ones may undergo modifications. The system must accommodate these changes without modifying the existing client codebase (Open-Closed Principle).

Implement the payment interface where a user chooses their preferred payment method and processes the transaction. Each payment method contains a function to process the payment, and prints a success confirmation.

---

## 2. Design Pattern & Architecture

### Why Factory Method?
The client application should not use `new CreditCardPayment()`, `new PayPalPayment()`, etc., directly. Centralizing instantiation in `PaymentMethodFactory` decouples transaction workflows from concrete payment gateways and facilitates seamless addition of new payment channels.

```
           +---------------------+
           |    PaymentMethod    | <------------------------+
           +---------------------+                          |
           | +processPayment()   |                          |
           +---------------------+                          |
                      ^                                     |
       +--------------+--------------+                      | creates
       |              |              |                      |
+-------------------+ +-------------+ +----------------+    |
| CreditCardPayment | | PayPalPayment| | BitcoinPayment |   |
+-------------------+ +-------------+ +----------------+    |
                                                            |
                                  +------------------------------+
                                  |     PaymentMethodFactory     |
                                  +------------------------------+
                                  | +createPaymentMethod(type)   |
                                  +------------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product Interface** | `PaymentMethod` | Declares `processPayment(double amount)`. |
| **Concrete Products** | `CreditCardPayment`, `PayPalPayment`, `BitcoinPayment` | Executes specific payment gateway logic and prints confirmations. |
| **Creator / Factory** | `PaymentMethodFactory` | Factory method resolving input strings into payment instances. |
| **Client** | `C1_PaymentFactory` | Initiates payments purely using `PaymentMethod` references. |

---

## 3. Solution Walkthrough

1. **Abstraction**: `PaymentMethod` provides a uniform payment contract.
2. **Channel Implementations**:
   - `CreditCardPayment`: Logs payment processing and confirms success via Credit Card.
   - `PayPalPayment`: Logs payment processing and confirms success via PayPal.
   - `BitcoinPayment`: Logs cryptocurrency transaction and confirms success via Bitcoin.
3. **Factory Resolution**: `createPaymentMethod(String type)` takes the identifier string and returns the concrete `PaymentMethod`.
4. **Extensibility**: Adding Google Pay or Apple Pay requires creating a new `PaymentMethod` class and adding a branch to `PaymentMethodFactory`, with no changes to caller code.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac C1_PaymentFactory.java
java C1_PaymentFactory
```

### Sample Output
```
Processing Credit Card payment of $150.0
Payment successful via Credit Card!
Processing PayPal payment of $75.5
Payment successful via PayPal!
Processing Bitcoin payment of $0.005
Payment successful via Bitcoin!
```
