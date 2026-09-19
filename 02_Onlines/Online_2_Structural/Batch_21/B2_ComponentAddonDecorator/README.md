# Batch 21 Online 2 (B2) - Component Feature Add-ons (Decorator Pattern)

## Problem Statement
A computer system is composed of several basic hardware components, such as a CPU, memory, storage, and a graphics card. Each component has a base price.

Customers can choose to add optional features to these components:
- **Extended Warranty**: Adds \$50 to the component price.
- **Installation Service**: Adds \$30 to the component price.
- **Performance Boost**: Adds \$80 to the component price.

**Task**: Use an appropriate design pattern to add features to components **without altering the core classes**.

Core classes provided:
```java
// Component interface representing the basic hardware component
interface Component {
    double getPrice();
    String getDescription();
}

// Concrete Component representing individual hardware components
class HardwareComponent implements Component {
    private String name;
    private double price;

    public HardwareComponent(String name, double price) {
        this.name = name;
        this.price = price;
    }
    @Override public double getPrice() { return price; }
    @Override public String getDescription() { return name; }
}
```

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern**

### Why Decorator?
- **Non-Invasive Enhancement**: The prompt mandates: *"add features to components without altering the core classes"*.
- **Stackable Upgrades**: A customer can choose any subset of add-on features in any order (e.g. CPU + Performance Boost + Extended Warranty).
- **Uniform Interface**: The wrapped component still implements `Component`, exposing both `getPrice()` and `getDescription()`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `Component` | Interface defining `getPrice()` and `getDescription()`. |
| **Concrete Component** | `HardwareComponent` | Core hardware part (CPU \$300, GPU \$500, etc.). |
| **Decorator** | `ComponentDecorator` | Abstract decorator holding a reference to `Component`. |
| **Concrete Decorators** | `ExtendedWarranty` (+\$50), `InstallationService` (+\$30), `PerformanceBoost` (+\$80) | Append feature cost to `getPrice()` and label to `getDescription()`. |
| **Client** | `B2` | Instantiates hardware and wraps with desired feature add-ons. |

---

## Class Architecture

```
                    <<interface>>
                      Component
               +getPrice(): double
               +getDescription(): String
                         ^
                         |
        +----------------+----------------+
        |                                 |
HardwareComponent                 ComponentDecorator
-name: String                     -wrappedComponent: Component
-price: double                    +getPrice(): double
+getPrice(): double               +getDescription(): String
+getDescription(): String                 ^
                                          |
        +-------------------+-------------+-------------------+
        |                   |                                 |
ExtendedWarranty     InstallationService              PerformanceBoost
(+$50)               (+$30)                           (+$80)
```

---

## Solution Walkthrough

1. **Abstract Decorator**:
   ```java
   abstract class ComponentDecorator implements Component {
       protected Component wrappedComponent;
       public ComponentDecorator(Component component) {
           this.wrappedComponent = component;
       }
       @Override public double getPrice() { return wrappedComponent.getPrice(); }
       @Override public String getDescription() { return wrappedComponent.getDescription(); }
   }
   ```
2. **Concrete Decorator Example (`ExtendedWarranty`)**:
   ```java
   class ExtendedWarranty extends ComponentDecorator {
       private static final double COST = 50;
       public ExtendedWarranty(Component component) { super(component); }
       @Override public double getPrice() { return wrappedComponent.getPrice() + COST; }
       @Override public String getDescription() { return wrappedComponent.getDescription() + " + Extended Warranty"; }
   }
   ```
3. **Usage**:
   ```java
   Component cpu = new HardwareComponent("CPU", 300);
   Component upgradedCpu = new PerformanceBoost(new ExtendedWarranty(cpu));
   // Output: CPU + Extended Warranty + Performance Boost -> 430.0
   ```

---

## How to Compile & Run

```bash
cd Batch_21/B2_ComponentAddonDecorator
javac *.java
java B2
```

### Output
```
CPU + Extended Warranty + Performance Boost -> 430.0
Graphics Card + Performance Boost + Extended Warranty + Installation Service -> 660.0
```
