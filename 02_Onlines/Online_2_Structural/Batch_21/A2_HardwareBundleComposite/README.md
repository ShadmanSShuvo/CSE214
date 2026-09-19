# Batch 21 Online 2 (A2) - Computer Hardware Bundles (Composite Pattern)

## Problem Statement
A computer system is composed of several hardware components, such as CPU, memory, storage, and graphics card. Each component has a price. Some customers buy individual components to build their custom computer, while others prefer pre-configured bundles (e.g., a gaming setup or workstation setup).

A bundle can contain several individual components or even another bundle (e.g., an "Ultimate Gaming Setup" may include a smaller "Basic Gaming Setup" along with extra hardware). Each bundle or individual component must be able to calculate its total price. Customers may also want to add or remove components from a bundle.

**Task**: Implement this system using an appropriate structural design pattern, where individual components and bundles are treated uniformly through one common interface.

---

## Design Pattern Analysis

### Pattern Applied: **Composite Pattern**

### Why Composite?
- **Part-Whole Hierarchies**: Computer systems naturally form a tree hierarchy where a bundle contains leaves (individual hardware parts) or other composite bundles.
- **Uniform Treatment**: Both individual parts (leaves) and bundles (composites) need to expose the same operations (`getPrice()`, `getName()`).
- **Recursive Price Calculation**: A bundle calculates its price by recursively summing the prices of all its child components without caring whether a child is a leaf or another composite.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `HardwareComponent` | Common interface declaring `getPrice()` and `getName()`. |
| **Leaf** | `IndividualComponent` | Represents single components (CPU, GPU, RAM, Storage, Fan). |
| **Composite** | `Bundle` | Container that holds components/bundles, supports `add()`, `remove()`, and calculates total price. |
| **Client** | `A2` | Creates components, builds nested bundles, and calculates prices. |

---

## Class Architecture

```
                 <<interface>>
               HardwareComponent
              +getPrice(): double
              +getName(): String
                     ^
                     |
        +------------+------------+
        |                         |
IndividualComponent            Bundle
-name: String             -name: String
-price: double            -components: List<HardwareComponent>
+getPrice(): double       +add(c: HardwareComponent)
+getName(): String        +remove(c: HardwareComponent)
                          +getPrice(): double
                          +printStructure(indent: String)
```

---

## Solution Walkthrough

1. **Uniform Interface**:
   ```java
   interface HardwareComponent {
       double getPrice();
       String getName();
   }
   ```
2. **Leaf (`IndividualComponent`)**:
   Returns its own unit price directly.
3. **Composite (`Bundle`)**:
   Maintains `List<HardwareComponent> components`. `getPrice()` iterates over children and sums their prices:
   ```java
   @Override
   public double getPrice() {
       double total = 0;
       for (HardwareComponent component : components) {
           total += component.getPrice();
       }
       return total;
   }
   ```
4. **Nested Bundles in Demo**:
   - `basicGamingSetup` = CPU ($300) + Memory ($100) + GPU ($500) = $900
   - `ultimateGamingSetup` = `basicGamingSetup` ($900) + Storage ($80) + Extra Fan ($40) = $1020
   - Removing extra fan $\rightarrow$ $980.

---

## How to Compile & Run

```bash
cd Batch_21/A2_HardwareBundleComposite
javac *.java
java A2
```

### Output
```
Basic Gaming Setup Price: 900.0
Ultimate Gaming Setup Price: 1020.0
Ultimate Gaming Setup Price after removing extra fan: 980.0

Structure:
Ultimate Gaming Setup -> 980.0
  Basic Gaming Setup -> 900.0
    CPU -> 300.0
    Memory -> 100.0
    Graphics Card -> 500.0
  Storage -> 80.0
```
