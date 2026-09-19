# Batch 19 Online 2 - Hogwarts Potions & Penalty System (Decorator Pattern)

## Problem Statement
At Hogwarts School of Witchcraft and Wizardry, students brew various magical potions under Professor Severus Snape's supervision. Every potion begins with a base solution called **Maker's Solution** consisting of:
- 25g White Spirit (\$1.23 per 10g $\rightarrow$ \$0.123/g)
- 25g Castor Oil (\$2.47 per 10g $\rightarrow$ \$0.247/g)
- Total base weight: 50g

Students can brew distinct advanced potions by adding specific magical ingredients on top of the base solution:
1. **Polyjuice Potion**: Adds 25g Poison Ivy (\$3.38 per 10g).
2. **Felix Felicis**: Adds 25g Unicorn Horn (\$6.31 per 10g).
3. **Veritaserum**: Adds 25g Dragon Kidney (\$5.86 per 10g).
4. **Skele-Gro**: Adds 25g Chinese Chomping Cabbage (\$4.13 per 10g).

For any botched batch, Snape docks House Points from Gryffindor based on ingredient waste:
- **Penalty Formula**: 2 penalty points per gram of total potion weight.

The system must dynamically compute total cost per jar, total weight, ingredient list, and penalty points for any potion configuration and batch size without hardcoding separate standalone classes for every possible potion combination.

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern (+ Factory Pattern)**

### Why Decorator?
- **Dynamic Feature Composition**: Advanced potions wrap the base `Maker's Solution` with extra ingredients, increasing the cost and weight dynamically.
- **Uniform Interface**: All potions (base or decorated) implement the common `Potion` interface, allowing clients and Snape's penalty calculator to treat all potions identically.
- **Open/Closed Principle**: New magical potions or extra ingredients can be added as new decorators without changing existing potion classes.
- **Complementary Factory**: A `PotionFactory` simplifies potion creation from user input strings while keeping the client code decoupled.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `Potion` | Common interface defining `getName()`, `getIngredients()`, `getCostPerJar()`, `getWeightPerJarGrams()`, `getPenaltyPointsPerJar()`. |
| **Concrete Component** | `MakersSolution` | Base potion (50g total weight: 25g White Spirit + 25g Castor Oil). |
| **Decorator** | `PotionDecorator` | Abstract decorator maintaining a reference to a wrapped `Potion`. |
| **Concrete Decorators** | `PolyjuicePotion`, `FelixFelicis`, `Veritaserum`, `SkeleGro` | Add specific magical ingredients, adjust total cost, weight, and names. |
| **Factory** | `PotionFactory` | Static factory instantiating the appropriate decorated potion object. |
| **Client** | `HogwartsPotionSystem` | CLI interface allowing users to order potion jars and inspect Snape's penalty report. |

---

## Class Architecture

```
                       <<interface>>
                          Potion
       +getName(): String
       +getIngredients(): List<String>
       +getCostPerJar(): double
       +getWeightPerJarGrams(): double
       +getPenaltyPointsPerJar(): double
             ^                          ^
             |                          |
       MakersSolution            PotionDecorator
       (Base Component)          -decoratedPotion: Potion
                                        ^
                                        |
       +-----------------+--------------+---------------+
       |                 |              |               |
 PolyjuicePotion    FelixFelicis   Veritaserum       SkeleGro
```

---

## Solution Walkthrough

1. **Base Component**:
   `MakersSolution` implements `Potion` and sets baseline weight (50g), ingredients, and cost calculation based on ingredient gram rates.
2. **Decorator Hierarchy**:
   `PotionDecorator` delegates base calls to the wrapped `Potion`.
3. **Ingredient Add-ons**:
   Each concrete decorator (`PolyjuicePotion`, `FelixFelicis`, etc.) overrides:
   - `getCostPerJar()`: `super.getCostPerJar() + (25.0 * rate)`
   - `getWeightPerJarGrams()`: `super.getWeightPerJarGrams() + 25.0`
   - `getPenaltyPointsPerJar()`: `getWeightPerJarGrams() * 2.0`
4. **Interactive CLI & Factory**:
   `PotionFactory.createPotion(potionName)` returns the assembled potion object.

---

## How to Compile & Run

```bash
cd Batch_19/HogwartsPotions_Decorator
javac *.java
java HogwartsPotionSystem
```

### Sample CLI Run
```
==================================================
   Hogwarts Potions & Penalty Management System
            Professor Severus Snape
==================================================
Enter Potion Name (Polyjuice, Felix Felicis, Veritaserum, Skele-Gro): Felix
Enter Quantity (Number of Jars): 2

----------------- POTION REPORT -----------------
Potion Name        : Felix Felicis
Quantity Requested : 2 Jar(s)
Required Ingredients:
  - White Spirit (25g)
  - Castor Oil (25g)
  - Unicorn Horn (25g)
--------------------------------------------------
Cost Per Jar       : $25.03
Total Cost         : $50.05
Total Weight       : 150.0g
Gryffindor Penalty : 300 Points (if botched)
--------------------------------------------------
```
