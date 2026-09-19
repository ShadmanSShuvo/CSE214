# Batch 22 Online 2 (C1) - Ramadan Package Add-ons (Decorator Pattern)

## Problem Statement
ZBazar traditionally offers three fixed Ramadan packages every year:
- **Standard Package**: Predefined contents, price = **1500 taka**.
- **Special Package**: Predefined contents, price = **2500 taka**.
- **Premium Package**: Predefined contents, price = **4000 taka**.

Recently, ZBazar received requests for slight customization. Customers want to add fruit, include sweets, or opt for premium gift packaging when sending packages to relatives. Instead of allowing full customization, ZBazar allows customers to optionally add:
1. **Fruit Package**: Adds **500 taka**.
2. **Sweet Package**: Adds **400 taka**.
3. **Premium Gift Packaging**: Adds **200 taka**.

These enhancements may be applied in **any combination**.

**Task**: Make the existing system capable of handling these seasonal add-ons without modifying the existing fixed packages. The system must correctly calculate the final price and description of the customized bundle while preserving the original package structure.

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern**

### Why Decorator?
- **Preserve Existing Structure**: Base packages (`StandardPackage`, `SpecialPackage`, `PremiumPackage`) remain untouched.
- **Any Combination**: With 3 add-ons, customers can choose any combination ($2^3 = 8$ variants per package). Decorators avoid class explosion ($3 \times 8 = 24$ subclasses).
- **Dynamic Responsibility**: Add-ons wrap the core package dynamically at checkout time, accumulating description and price.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `RamadanPackage` | Interface defining `getPrice(): double` and `getDescription(): String`. |
| **Concrete Components** | `StandardPackage`, `SpecialPackage`, `PremiumPackage` | Existing core packages with fixed base prices. |
| **Decorator** | `PackageDecorator` | Abstract class holding a `RamadanPackage wrappee`. |
| **Concrete Decorators** | `FruitAddOn` (+500), `SweetAddOn` (+400), `GiftPackagingAddOn` (+200) | Append cost and description. |
| **Client** | `C1Decorator` | Wraps packages dynamically based on customer selections. |

---

## Class Architecture

```
                    <<interface>>
                   RamadanPackage
               +getPrice(): double
               +getDescription(): String
                         ^
                         |
        +----------------+----------------+
        |                                 |
  SpecialPackage                   PackageDecorator
  (also Standard, Premium)         -wrappee: RamadanPackage
                                          ^
                                          |
        +-------------------+-------------+-------------------+
        |                   |                                 |
    FruitAddOn          SweetAddOn                    GiftPackagingAddOn
    (+$500)             (+$400)                       (+$200)
```

---

## Solution Walkthrough

1. **Base Component & Packages**:
   ```java
   interface RamadanPackage {
       double getPrice();
       String getDescription();
   }
   class SpecialPackage implements RamadanPackage {
       public double getPrice() { return 2500; }
       public String getDescription() { return "Special Ramadan Package"; }
   }
   ```
2. **Decorators**:
   ```java
   abstract class PackageDecorator implements RamadanPackage {
       protected RamadanPackage wrappee;
       PackageDecorator(RamadanPackage wrappee) { this.wrappee = wrappee; }
   }
   class FruitAddOn extends PackageDecorator {
       FruitAddOn(RamadanPackage w) { super(w); }
       public double getPrice() { return wrappee.getPrice() + 500; }
       public String getDescription() { return wrappee.getDescription() + " + Fruit Package"; }
   }
   ```
3. **Chaining at Runtime**:
   ```java
   RamadanPackage order = new GiftPackagingAddOn(
                              new FruitAddOn(
                                  new SpecialPackage()));
   // Price = 2500 + 500 + 200 = 3200
   ```

---

## How to Compile & Run

```bash
cd Batch_22/C1_RamadanPackageDecorator
javac *.java
java C1Decorator
```

### Output
```
Special Ramadan Package + Fruit Package + Premium Gift Packaging
Total: 3200.0
```
