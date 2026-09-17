# Problem Statement: HungryHippo Food Delivery Order Lifecycle

**Batch**: CSE21
**Source**: `Online 3 C2.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **State Pattern**

---

## 1. Problem Description

You are an employee at HungryHippo, a leading online food delivery app known for its seamless shopping experience. Every customer order goes through a journey involving multiple steps:
- **"Placed"**: Awaiting restaurant confirmation.
- **"Confirmed"**: Kitchen confirmed the order, preparation underway.
- **"Shipped"**: Courier picked up the food; order is on its way.
- **"Delivered"**: Order successfully delivered to the customer.
- **"Cancelled"**: Order cancelled prior to shipment.

### Lifecycle Rules:
1. When an order is first placed, it enters the **"Placed"** state.
2. Once confirmed, it moves to **"Confirmed"**.
3. From "Confirmed", it moves to **"Shipped"**.
4. Finally, it moves to **"Delivered"**, marking successful completion.
5. Orders may be cancelled **before shipment** (from either "Placed" or "Confirmed"), moving to **"Cancelled"**.
6. **Enforce valid transitions**:
   - An order cannot skip directly from "Placed" to "Delivered".
   - An order cannot revert to "Confirmed" once it has been "Shipped".
   - An order cannot be cancelled once it has been "Shipped" or "Delivered".
7. **Extensibility**: The design must allow future addition of states like "Returned" or "On Hold" with minimal modifications.

---

## 2. Design Pattern Justification: State Pattern

- **Why State Pattern?**:
  An order behaves differently based on its current state. By encapsulating state-specific behavior and transition logic inside separate state classes, we eliminate monolithic `if-else` or `switch-case` statements in `Order`.
- **Open/Closed Principle**: Adding a new state (e.g., `OnHoldState`, `ReturnedState`) only requires creating a new class implementing `OrderState` and adjusting allowable transition hooks without altering other states.

---

## 3. State Transition Diagram

```
       +--------------+
       |    Placed    |------+
       +--------------+      |
              |              |
              v              v
       +--------------+  +-------------+
       |  Confirmed   |->|  Cancelled  | (Terminal)
       +--------------+  +-------------+
              |
              v
       +--------------+
       |   Shipped    |
       +--------------+
              |
              v
       +--------------+
       |  Delivered   | (Terminal)
       +--------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE21/HungryHippoOrder
javac *.java
java Main
```
