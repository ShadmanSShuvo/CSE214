# Batch 22 Online 2 (C2) - ZBazar Grocery Delivery Logistics (Bridge Pattern)

## Problem Statement
ZBazar is an online grocery platform that offers multiple delivery options:
- **Standard Delivery** (delivered within 24 hours)
- **Express Delivery** (delivered within 4 hours)
- **Scheduled Delivery** (delivered at a chosen time slot)

Each delivery type has its own pricing logic and estimated delivery time calculation. Regardless of the delivery type, every order must ultimately be dispatched using a physical transportation method.

Until now, ZBazar has relied on:
- **Bike Courier**
- **Van Delivery**

Recently, the company decided to introduce **Drone Delivery** in selected cities and plans to introduce **Robot Delivery** in smart neighborhoods. The development team finds itself modifying multiple classes just to support a new transport method, leading to code duplication and tight coupling.

**Challenge**: Business policies for delivery types change frequently, while transport technologies expand over time.

**Task**: Redesign the system using an appropriate design pattern so that new transport technologies can be introduced without rewriting existing delivery-type logic, and vice versa.

---

## Design Pattern Analysis

### Pattern Applied: **Bridge Pattern**

### Why Bridge?
- **Two Independent Dimensions of Variation**:
  1. **Delivery Type (Abstraction)**: Business policies, time commitments, and scheduling (`StandardDelivery`, `ExpressDelivery`, `ScheduledDelivery`).
  2. **Transport Method (Implementor)**: Physical vehicle dispatch and technology (`BikeCourier`, `VanDelivery`, `DroneDelivery`, `RobotDelivery`).
- **Independent Extension**: Adding a new vehicle (e.g., `AutonomousVehicle`) requires zero modifications to delivery types. Adding a new delivery type (e.g., `SameHourDelivery`) requires zero modifications to vehicle dispatchers.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Abstraction** | `DeliveryType` | Maintains reference to `TransportMethod`; declares `deliver(String orderId)`. |
| **Refined Abstractions** | `StandardDelivery`, `ExpressDelivery`, `ScheduledDelivery` | Implement specific delivery policies and delegate dispatch. |
| **Implementor** | `TransportMethod` | Interface declaring `void dispatch(String orderId)`. |
| **Concrete Implementors** | `BikeCourier`, `VanDelivery`, `DroneDelivery`, `RobotDelivery` | Handle vehicle-specific dispatch and physical logistics. |
| **Client** | `C2Bridge` | Pairs delivery types with transport methods dynamically. |

---

## Class Architecture

```
        DeliveryType (Abstraction)   ----[bridge]---->   TransportMethod (Implementor)
        -transport: TransportMethod                      +dispatch(orderId: String)
        +deliver(orderId: String)                                      ^
                    ^                                                  |
        +-----------+-----------+                     +--------+-------+--------+
        |           |           |                     |        |       |        |
    Standard     Express    Scheduled                Bike     Van    Drone    Robot
```

---

## Solution Walkthrough

1. **Implementor Hierarchy**:
   ```java
   interface TransportMethod {
       void dispatch(String orderId);
   }
   class DroneDelivery implements TransportMethod {
       public void dispatch(String orderId) {
           System.out.println("Order " + orderId + " dispatched via Drone (safety checks passed)");
       }
   }
   class RobotDelivery implements TransportMethod {
       public void dispatch(String orderId) {
           System.out.println("Order " + orderId + " dispatched via Robot");
       }
   }
   ```
2. **Abstraction Hierarchy**:
   ```java
   abstract class DeliveryType {
       protected TransportMethod transport;
       DeliveryType(TransportMethod transport) { this.transport = transport; }
       abstract void deliver(String orderId);
   }
   class ExpressDelivery extends DeliveryType {
       ExpressDelivery(TransportMethod t) { super(t); }
       void deliver(String orderId) {
           System.out.println("Express Delivery (within 4h) for " + orderId);
           transport.dispatch(orderId);
       }
   }
   ```
3. **Decoupled Execution**:
   ```java
   DeliveryType d1 = new ExpressDelivery(new DroneDelivery());
   d1.deliver("ORD123");

   DeliveryType d2 = new StandardDelivery(new BikeCourier());
   d2.deliver("ORD124");
   ```

---

## How to Compile & Run

```bash
cd Batch_22/C2_ZBazarDeliveryBridge
javac *.java
java C2Bridge
```

### Output
```
Express Delivery (within 4h) for ORD123
Order ORD123 dispatched via Drone (safety checks passed)
Standard Delivery (within 24h) for ORD124
Order ORD124 dispatched via Bike
```
