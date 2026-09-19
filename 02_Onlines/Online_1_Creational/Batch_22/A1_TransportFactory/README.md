# Batch 22 - Section A1: Logistics Transport System

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Factory Method Pattern
- **Language:** Java

---

## 1. Problem Statement

You are designing a logistics management system that supports multiple modes of delivery (e.g., Road and Sea). Each transport mode executes a `deliver()` operation:
- **Road:** Delivers by land using a Truck.
- **Sea:** Delivers by sea using a Cargo Ship.

The client specifies the delivery mode via a string parameter. The system must create and return the appropriate transport object without exposing concrete classes to the client. The design should easily support adding new transport modes (e.g., Air) without modifying existing transport classes.

---

## 2. Design Pattern & Architecture

### Why Factory Method?
The client application should not directly instantiate concrete transport classes. The **Factory Method Pattern** centralizes object creation in `TransportFactory`, allowing seamless runtime extension.

```
           +--------------------+
           |     Transport      | <------------------------+
           +--------------------+                          |
           | +deliver()         |                          |
           +--------------------+                          |
                     ^                                     |
       +-------------+-------------+                       | creates
       |             |             |                       |
   +-------+     +------+     +----------+                 |
   | Truck |     | Ship |     | Airplane |                 |
   +-------+     +------+     +----------+                 |
                                                           |
                                  +------------------------------+
                                  |       TransportFactory       |
                                  +------------------------------+
                                  | +createTransport(mode)       |
                                  +------------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product Interface** | `Transport` | Interface declaring delivery execution (`deliver()`). |
| **Concrete Products** | `Truck`, `Ship`, `Airplane` | Specific transport vehicles with custom delivery mechanics. |
| **Creator / Factory** | `TransportFactory` | Static factory method creating concrete vehicles based on requested mode. |
| **Client** | `A1_TransportFactory` | Calls `TransportFactory.createTransport()` and delegates delivery. |

---

## 3. Solution Walkthrough

1. **Common Product Interface (`Transport`)**: Declares `void deliver()`.
2. **Concrete Transports**:
   - `Truck`: Outputs `"Delivering by land in a Truck."`
   - `Ship`: Outputs `"Delivering by sea in a Ship."`
   - `Airplane`: Outputs `"Delivering by air in an Airplane."` (demonstrating extensibility)
3. **Factory Method (`createTransport`)**:
   - Matches mode `"Road"`, `"Sea"`, or `"Air"`.
   - Returns the respective concrete instance.
   - Throws `IllegalArgumentException` for unknown modes.
4. **Client Invariant**: The client never calls `new Truck()` or `new Ship()`.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac A1_TransportFactory.java
java A1_TransportFactory
```

### Sample Output
```
Delivering by land in a Truck.
Delivering by sea in a Ship.
Delivering by air in an Airplane.
```
