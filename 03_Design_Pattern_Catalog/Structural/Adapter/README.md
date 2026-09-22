# Adapter Pattern

## 📌 Intent
Convert the interface of a class into another interface that clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

---

## 🏗️ Architecture & Class Diagram (Object Adapter)

```
        Client
          │
          ▼ uses
    +--------------------+                  +---------------------+
    |     <<Target>>     |                  |     <<Adaptee>>     |
    |  PaymentProcessor  |                  |    StripeGateway    |
    +--------------------+                  +---------------------+
    | +processPayment()  |                  | +chargeCard(...)    |
    | +refund()          |                  | +reverseCharge(...) |
    +--------------------+                  +---------------------+
              ▲                                        ▲
              │ implements                             │ wraps
    +--------------------+                             │
    |   StripeAdapter    | ────────────────────────────┘
    +--------------------+
    | - adaptee: Stripe  |
    | +processPayment()  | ──> adaptee.chargeCard(...)
    | +refund()          | ──> adaptee.reverseCharge(...)
    +--------------------+
```

---

## 📁 Implementations in this Directory

| File | Scenario | Key Features |
| :--- | :--- | :--- |
| **`AdapterDemo.java`** | Audio Player (`MediaPlayer`) adapting `AdvancedPlayer` (VLC / MP4) | Basic object adapter illustrating delegation and format checking. |
| **`AdapterComplexDemo.java`** | Multi-Gateway E-commerce Checkout (`PaymentProcessor`) | Adapts Stripe, PayPal, and Cryptocurrency exchange APIs into uniform checkout and refund pipelines. |

---

## 🚀 How to Compile & Run
```bash
# Basic Media Player Adapter
javac AdapterDemo.java
java AdapterDemo

# Enterprise Payment Gateway Adapter
javac AdapterComplexDemo.java
java AdapterComplexDemo
```
