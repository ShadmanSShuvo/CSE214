# Batch 23 Online 2 (B2) - Meal & Grocery Bundle (Composite Pattern)

## Problem Statement
A retail shop originally sold only individual food items (e.g., burgers, pizzas, sandwiches, fries). Consequently, a customer order was simply maintained as a collection of food items.

As the business expanded, the shop introduced two new product categories:
1. **Grocery Items** (e.g., rice, flour, cooking oil, eggs), which are sold individually or as bundled packages (`GroceryPackage`). These packages contain individual grocery items or other nested packages.
2. **Set Menus**, which are predefined meal combinations consisting of one or more individual food items.
   - **Rule 1**: A set menu *cannot* contain another set menu or any grocery items.
   - **Rule 2**: The price of a set menu is **10 percent less** than the total price of the individual food items ($0.9 \times \text{sum}$).

A customer order can now include any combination of:
- Individual food items
- Individual grocery items
- One or more set menus
- One or more grocery packages

During checkout, the system must process all purchased items **uniformly**, regardless of whether they are individual items, set menus, or nested grocery packages.

---

## Design Pattern Analysis

### Pattern Applied: **Composite Pattern**

### Why Composite?
- **Uniform Order Item Interface**: The provided `Order` class maintains a simple list of `OrderItem` objects:
  ```java
  public class Order {
      private List<OrderItem> items = new ArrayList<>();
      public void add(OrderItem item) { items.add(item); }
      public double getTotalPrice() { ... }
      public void printReceipt() { ... }
  }
  ```
- **Hierarchical Bundles**:
  - `Food` (Leaf)
  - `Grocery` (Leaf)
  - `SetMenu` (Composite of Food only, applies 10% bundle discount)
  - `GroceryPackage` (Composite of Grocery items and other GroceryPackages)
- **Constraint Enforcement**: `SetMenu.addFood(Food f)` strictly allows adding only `Food` instances, enforcing the domain rule that set menus cannot contain groceries or nested set menus.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `OrderItem` | Common interface defining `getPrice(): double` and `print(indent: String): void`. |
| **Leaf 1** | `Food` | Represents individual food items (`name`, `price`). |
| **Leaf 2** | `Grocery` | Represents individual grocery items (`name`, `price`). |
| **Composite 1** | `SetMenu` | Collection of `Food` items with 10% discount on total price. |
| **Composite 2** | `GroceryPackage` | Collection of `Grocery` items and nested `GroceryPackage` bundles. |
| **Client / Context**| `Order`, `Main` | Processes order items uniformly and generates receipt. |

---

## Class Architecture

```
                    <<interface>>
                      OrderItem
               +getPrice(): double
               +print(indent: String): void
                         ^
                         |
        +----------------+----------------+
        |                |                |
      Food            Grocery          SetMenu            GroceryPackage
  (Food Leaf)     (Grocery Leaf)    (Food Composite)   (Grocery Composite)
                                    -foods: List<Food> -items: List<OrderItem>
                                    -price: 0.9 * sum  -price: sum(children)
```

---

## Solution Walkthrough

1. **Component Contract (`OrderItem.java`)**:
   ```java
   public interface OrderItem {
       double getPrice();
       void print(String indent);
   }
   ```
2. **Leaves (`Food.java` & `Grocery.java`)**:
   Format their own output lines: `Food: Burger (£8.00)`, `Grocery: Rice (£20.00)`.
3. **Set Menu Composite (`SetMenu.java`)**:
   Enforces type safety by accepting only `Food`:
   ```java
   public void addFood(Food food) { foods.add(food); }
   @Override
   public double getPrice() {
       double sum = 0;
       for (Food f : foods) sum += f.getPrice();
       return sum * 0.90; // 10% discount
   }
   ```
4. **Grocery Package Composite (`GroceryPackage.java`)**:
   Recursively calculates price and indents child grocery items and nested packs.

---

## How to Compile & Run

```bash
cd Batch_23/B2_MealGroceryBundle
javac *.java
java Main
```

### Verified Output
```
========== RECEIPT ==========
Food: Pizza (£10.00)
Set Menu: Lunch Combo
 Food: Burger (£8.00)
 Food: French Fries (£3.00)
Grocery: Rice (£20.00)
Package: Monthly Essentials
 Grocery: Rice (£20.00)
 Grocery: Cooking Oil (£12.00)
 Package: Breakfast Pack
  Grocery: Eggs (£6.00)
  Grocery: Sugar (£5.00)
-----------------------------
Total Bill: £82.90
```
*(Lunch Combo = (£8 + £3) * 0.9 = £9.90; Total = £10.00 + £9.90 + £20.00 + £43.00 = £82.90)*
