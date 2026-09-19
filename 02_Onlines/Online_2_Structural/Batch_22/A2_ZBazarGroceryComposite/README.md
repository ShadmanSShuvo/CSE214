# Batch 22 Online 2 (A2) - ZBazar Subscription Grocery Bundles (Composite Pattern)

## Problem Statement
ZBazar is a subscription-based grocery delivery service that lets customers set up a recurring monthly bazar bundle that is automatically delivered to their homes.

The platform offers:
- **Preset Packages** (such as Small, Family, Mega) consisting of multiple single items.
- **Single Grocery Items** (individual items like rice, oil, pulse, etc.), each having a specific name, price, and weight.

Users can create or modify their own **Custom Bazar** by combining:
- One or more preset packages,
- Single items,
- Mixture of both,
- Previously created custom packages (nested composition).

The system must support:
1. Calculating the total price of any configuration.
2. Calculating the total weight of any configuration.
3. Displaying the complete hierarchical structure of the custom package.

**Task**: Choose the appropriate design pattern to solve this problem and implement a minimal demonstration.

---

## Design Pattern Analysis

### Pattern Applied: **Composite Pattern**

### Why Composite?
- **Recursive Tree Structure**: Packages contain items or other packages in arbitrary nesting depths.
- **Uniform Interface**: Both a single grocery item (`SingleItem`) and a package (`CustomBazar`) implement `BazarComponent`, exposing `getPrice()`, `getWeight()`, and `display(String indent)`.
- **Simplifies Client Code**: The client does not need to distinguish between leaf items and composite packages when calculating costs or rendering trees.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `BazarComponent` | Interface defining `getPrice()`, `getWeight()`, `display(indent)`. |
| **Leaf** | `SingleItem` | Individual grocery item (name, price, weight). |
| **Composite** | `CustomBazar` | Collection of `BazarComponent` items/packages; sums prices and weights recursively. |
| **Client** | `A2Composite` | Builds custom bundles and displays totals. |

---

## Class Architecture

```
                  <<interface>>
                 BazarComponent
              +getPrice(): double
              +getWeight(): double
              +display(indent: String): String
                     ^
                     |
       +-------------+-------------+
       |                           |
   SingleItem                 CustomBazar
-name: String             -name: String
-price: double            -children: List<BazarComponent>
-weight: double           +add(c: BazarComponent)
+getPrice(): double       +getPrice(): double (sum of children)
+getWeight(): double      +getWeight(): double (sum of children)
                          +display(indent): String
```

---

## Solution Walkthrough

1. **Component Interface**:
   ```java
   interface BazarComponent {
       double getPrice();
       double getWeight();
       String display(String indent);
   }
   ```
2. **Leaf (`SingleItem`)**:
   Holds `name`, `price`, `weight`. Returns its own values.
3. **Composite (`CustomBazar`)**:
   Sums child components using Java Stream API:
   ```java
   public double getPrice() {
       return children.stream().mapToDouble(BazarComponent::getPrice).sum();
   }
   public double getWeight() {
       return children.stream().mapToDouble(BazarComponent::getWeight).sum();
   }
   ```
4. **Nested Demo**:
   - `smallPackage` = Rice (\$60, 5kg) + Oil (\$180, 2kg) = \$240, 7kg.
   - `myBazar` = `smallPackage` + Pulse (\$90, 1kg) + `smallPackage` (reused) = \$570, 15kg.

---

## How to Compile & Run

```bash
cd Batch_22/A2_ZBazarGroceryComposite
javac *.java
java A2Composite
```

### Output
```
My Custom Bazar:
  Small Package:
    Rice ($60.0, 5.0kg)
    Oil ($180.0, 2.0kg)
  Pulse ($90.0, 1.0kg)
  Small Package:
    Rice ($60.0, 5.0kg)
    Oil ($180.0, 2.0kg)

Total Price: 570.0
Total Weight: 15.0
```
