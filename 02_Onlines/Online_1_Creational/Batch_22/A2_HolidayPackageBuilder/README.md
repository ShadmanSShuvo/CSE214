# Batch 22 - Section A2: Travel Agency Holiday Package Builder

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Builder Pattern
- **Language:** Java

---

## 1. Problem Statement

A travel agency offers customizable holiday packages consisting of:
- A **Flight** (e.g., Business Class, Economy)
- A **Hotel** (e.g., 5-Star Resort, Mountain Cabin)
- A **Daily Activity** (e.g., Spa Treatment, Hiking Tour)

The agency provides pre-configured standard packages:
1. **Relaxation Package:** Business Class Flight + 5-Star Resort + Spa Treatment
2. **Adventure Package:** Economy Flight + Mountain Cabin + Hiking Tour

Use the **Builder Pattern** to construct `HolidayPackage` objects step by step. A `Director` must control the execution sequence of construction steps.

---

## 2. Design Pattern & Architecture

### Why Builder Pattern?
Constructing complex composite packages requires consistent, ordered initialization of attributes. The **Builder Pattern** separates configuration details from the final representation and uses `TravelAgencyDirector` to enforce standard assembly workflows.

```
       +-----------------------+
       |  TravelAgencyDirector |
       +-----------------------+
       | -builder              |
       | +constructPackage()   |
       +-----------------------+
                   | delegates steps
                   v
       +-------------------------------+
       |     HolidayPackageBuilder     | <-------------------------+
       +-------------------------------+                           |
       | +buildFlight()                |                           |
       | +buildHotel()                 |                           |
       | +buildActivity()              |                           |
       | +getPackage()                 |                           |
       +-------------------------------+                           |
                       ^                                           |
           +-----------+-----------+                               |
           |                       |                               |
+--------------------------+ +-------------------------+           |
| RelaxationPackageBuilder | | AdventurePackageBuilder |           |
+--------------------------+ +-------------------------+           |
                                                                   | builds
                                                                   v
                                                        +--------------------+
                                                        |   HolidayPackage   |
                                                        +--------------------+
                                                        | -flight            |
                                                        | -hotel             |
                                                        | -activity          |
                                                        +--------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product** | `HolidayPackage` | Target product containing flight, hotel, and activity details. |
| **Builder Interface** | `HolidayPackageBuilder` | Defines steps: `buildFlight`, `buildHotel`, `buildActivity`, `getPackage`. |
| **Concrete Builders** | `RelaxationPackageBuilder`, `AdventurePackageBuilder` | Configures package components for specific holiday themes. |
| **Director** | `TravelAgencyDirector` | Enforces the 3-step construction sequence. |
| **Client** | `A2_HolidayPackageBuilder` | Passes concrete builders to the director and retrieves finalized packages. |

---

## 3. Solution Walkthrough

1. **Complex Product (`HolidayPackage`)**: Houses package details and formats them with `toString()`.
2. **Builder Interface (`HolidayPackageBuilder`)**: Specifies standard assembly hooks.
3. **Themes**:
   - `RelaxationPackageBuilder`: Builds Business Class flight, 5-Star resort, and Spa treatment.
   - `AdventurePackageBuilder`: Builds Economy flight, Mountain cabin, and Hiking tour.
4. **Director (`constructPackage`)**: Executes:
   ```java
   builder.buildFlight();
   builder.buildHotel();
   builder.buildActivity();
   return builder.getPackage();
   ```

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac A2_HolidayPackageBuilder.java
java A2_HolidayPackageBuilder
```

### Sample Output
```
HolidayPackage [Flight=Business Class Flight, Hotel=5-Star Resort, Activity=Spa Treatment]
HolidayPackage [Flight=Economy Flight, Hotel=Mountain Cabin, Activity=Hiking Tour]
```
