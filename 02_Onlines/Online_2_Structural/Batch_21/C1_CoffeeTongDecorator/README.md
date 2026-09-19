# Batch 21 Online 2 (C1) - CoffeeTong Beverage Customization (Decorator Pattern)

## Problem Statement
In your locality, there's a cozy coffee place named CoffeeTong that serves two primary coffee variations:
- **Americano**: A type of black coffee prepared by adding extra grinded coffee beans to regular black coffee.
- **Cappuccino**: A type of milk coffee prepared by adding cinnamon powder to regular milk coffee.

### Preparation Details
- **Black Coffee**: Prepared using water and grinded coffee beans.
- **Milk Coffee**: Prepared using milk and grinded coffee beans.

### Cost Breakdown
- CoffeeTong serves coffee in handmade fancy mugs: **100 taka**.
- Grinded coffee beans (used in both types): **30 taka**.
- Milk (used in milk coffee only): **50 taka**.
- Extra ingredients:
  - Americano: Additional grinded coffee beans: **30 taka**.
  - Cappuccino: Cinnamon powder: **50 taka**.

### Requirements
Implement a program using an appropriate design pattern to manage different coffee types and their variations, ensuring that the coffee preparation process is **easily extendable** if new coffee types (e.g., Espresso, Mocha) are added in the future.

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern**

### Why Decorator?
- **Base Components vs. Enhancements**:
  - Base coffees: `BasicBlackCoffee` (Mug 100 + Coffee Beans 30 = 130 taka) and `BasicMilkCoffee` (Mug 100 + Coffee Beans 30 + Milk 50 = 180 taka).
  - Variations are ingredient decorators: `Americano` wraps a black coffee and adds extra beans (+30 taka); `Cappuccino` wraps a milk coffee and adds cinnamon powder (+50 taka).
- **Future Extensibility**: If CoffeeTong adds Espresso or Mocha, developers can simply implement new decorators (e.g. `Mocha` adding Chocolate Syrup) without modifying existing coffee classes.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `Coffee` | Interface defining `getIngredients(): String` and `getCost(): int`. |
| **Concrete Components** | `BasicBlackCoffee`, `BasicMilkCoffee` | Provide base mug and fundamental ingredients. |
| **Decorator** | `CoffeeDecorator` | Abstract decorator wrapping a `Coffee` instance. |
| **Concrete Decorators** | `Americano`, `Cappuccino` | Add specific ingredients and costs to the wrapped base coffee. |
| **Context** | `Order` | Manages list of customized coffees and calculates total order cost. |
| **Client** | `C1` | Interactive scanner loop taking customer orders. |

---

## Class Architecture

```
                    <<interface>>
                       Coffee
              +getIngredients(): String
              +getCost(): int
                     ^
                     |
       +-------------+-------------+
       |                           |
BasicBlackCoffee            CoffeeDecorator
(Mug + Beans = 130)         -wrappedCoffee: Coffee
                                   ^
                                   |
                  +----------------+----------------+
                  |                                 |
              Americano                         Cappuccino
              (+Extra Beans = +30)              (+Cinnamon = +50)
```

---

## Solution Walkthrough

1. **Base Concrete Components**:
   - `BasicBlackCoffee`: ingredients `"Mug, Water, Grinded Coffee Beans"`, cost = 130.
   - `BasicMilkCoffee`: ingredients `"Mug, Milk, Grinded Coffee Beans"`, cost = 180.
2. **Decorator Hierarchy**:
   ```java
   abstract class CoffeeDecorator implements Coffee {
       protected Coffee wrappedCoffee;
       public CoffeeDecorator(Coffee coffee) { this.wrappedCoffee = coffee; }
       @Override public String getIngredients() { return wrappedCoffee.getIngredients(); }
       @Override public int getCost() { return wrappedCoffee.getCost(); }
   }
   ```
3. **Adding Add-ons**:
   - `Americano` appends `", Extra Grinded Coffee Beans"` and adds 30 taka (Total: 160 taka).
   - `Cappuccino` appends `", Cinnamon Powder"` and adds 50 taka (Total: 230 taka).

---

## How to Compile & Run

```bash
cd Batch_21/C1_CoffeeTongDecorator
javac *.java
java C1
```

### Sample Interactive Order
```
Select coffee type (1: Americano, 2: Espresso, 3: Cappuccino, 4: Mocha, 0: Finish): 1
Select coffee type (1: Americano, 2: Espresso, 3: Cappuccino, 4: Mocha, 0: Finish): 3
Select coffee type (1: Americano, 2: Espresso, 3: Cappuccino, 4: Mocha, 0: Finish): 0

Coffee 1:
Ingredients: Mug, Water, Grinded Coffee Beans, Extra Grinded Coffee Beans
Cost: 160 taka

Coffee 2:
Ingredients: Mug, Milk, Grinded Coffee Beans, Cinnamon Powder
Cost: 230 taka

Total Cost for Order: 390 taka
```
