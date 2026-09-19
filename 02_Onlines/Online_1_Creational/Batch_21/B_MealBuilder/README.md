# Batch 21 - Section B: Restaurant Meal Plan Builder

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Builder Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing a system to build meal plans for a restaurant. There are two kinds of meals available:
1. **Bengali Meal**: Vegetable (starter), Chicken Curry (main dish), Sweet Curd (dessert).
2. **Chinese Meal**: Soup (starter), Peking Duck (main dish), Pudding (dessert).

After the user chooses their preferred meal, the system must create the meal with all three courses using the appropriate design pattern. You do not need to create individual classes for each course (strings can represent them), but you must create a class that represents the final meal with all its courses.

---

## 2. Design Pattern & Architecture

### Why Builder Pattern?
Constructing a `Meal` requires a multi-step sequence (`buildStarter()`, `buildMainDish()`, `buildDessert()`). The **Builder Pattern** separates the complex step-by-step construction process from the final `Meal` representation, while a `MealDirector` orchestrates the uniform execution order.

```
       +------------------+
       |   MealDirector   |
       +------------------+
       | -builder         |
       | +constructMeal() |
       +------------------+
                | delegates steps
                v
       +------------------+
       |   MealBuilder    | <------------------+
       +------------------+                    |
       | +buildStarter()  |                    |
       | +buildMainDish() |                    |
       | +buildDessert()  |                    |
       | +getMeal()       |                    |
       +------------------+                    |
                ^                              |
        +-------+-------+                      |
        |               |                      |
+--------------------+ +-------------------+   | builds
| BengaliMealBuilder | | ChineseMealBuilder|   |
+--------------------+ +-------------------+   |
                                               v
                                        +--------------+
                                        |     Meal     |
                                        +--------------+
                                        | -starter     |
                                        | -mainDish    |
                                        | -dessert     |
                                        +--------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product** | `Meal` | Complex product holding starter, main dish, and dessert courses. |
| **Builder Interface** | `MealBuilder` | Declares the stepwise assembly interface (`buildStarter`, `buildMainDish`, `buildDessert`, `getMeal`). |
| **Concrete Builders** | `BengaliMealBuilder`, `ChineseMealBuilder` | Implements course assembly using specific menu items. |
| **Director** | `MealDirector` | Enforces the invariant assembly sequence across different builder implementations. |
| **Client** | `B_MealBuilder` | Selects the builder, passes it to the director, and receives the built product. |

---

## 3. Solution Walkthrough

1. **Complex Product (`Meal`)**: Encapsulates private fields for `starter`, `mainDish`, and `dessert`, with setters and a formatted `toString()` method.
2. **Stepwise Assembly (`MealBuilder`)**: Provides granular building steps and a `getMeal()` extraction method.
3. **Menu Variations**:
   - `BengaliMealBuilder`: Sets `starter="Vegetable"`, `mainDish="Chicken Curry"`, `dessert="Sweet Curd"`.
   - `ChineseMealBuilder`: Sets `starter="Soup"`, `mainDish="Peking Duck"`, `dessert="Pudding"`.
4. **Director Execution (`constructMeal()`)**:
   ```java
   builder.buildStarter();
   builder.buildMainDish();
   builder.buildDessert();
   return builder.getMeal();
   ```
5. **Polymorphic Reconfiguration**: The client can swap builders dynamically using `director.setBuilder(...)`.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac B_MealBuilder.java
java B_MealBuilder
```

### Sample Output
```
Meal [Starter=Vegetable, Main Dish=Chicken Curry, Dessert=Sweet Curd]
Meal [Starter=Soup, Main Dish=Peking Duck, Dessert=Pudding]
```
