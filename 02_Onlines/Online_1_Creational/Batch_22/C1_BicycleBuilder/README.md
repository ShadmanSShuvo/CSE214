# Batch 22 - Section C1: Bicycle Assembly Factory

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Builder Pattern
- **Language:** Java

---

## 1. Problem Statement

A bicycle manufacturing plant produces different models of bicycles:
- **The Commuter:** Aluminum Frame, Single Speed Gear, Road Tires.
- **The Mountain Beast:** Carbon Fiber Frame, 12-Speed Gear, Off-road Grip Tires.

Each bicycle comprises three key components: Frame, Gear System, and Tires.

Use the **Builder Pattern** to construct `Bicycle` objects step by step. A `Director` class must coordinate the assembly sequence so that any bicycle model can be produced using the same uniform assembly workflow.

---

## 2. Design Pattern & Architecture

### Why Builder Pattern?
Creating custom bicycles requires configuring multiple interrelated attributes (`frame`, `gearSystem`, `tireType`). The **Builder Pattern** encapsulates the component assembly logic within specialized builder classes and uses `BicycleFactoryDirector` to enforce an invariant construction sequence.

```
       +--------------------------+
       |  BicycleFactoryDirector  |
       +--------------------------+
       | -builder                 |
       | +constructBicycle()      |
       +--------------------------+
                    | executes assembly steps
                    v
       +--------------------------+
       |      BicycleBuilder      | <--------------------------+
       +--------------------------+                            |
       | +buildFrame()            |                            |
       | +buildGearSystem()       |                            |
       | +buildTireType()         |                            |
       | +getBicycle()            |                            |
       +--------------------------+                            |
                    ^                                          |
          +---------+---------+                                |
          |                   |                                |
+------------------+ +----------------------+                  |
| CommuterBuilder  | | MountainBeastBuilder |                  |
+------------------+ +----------------------+                  |
                                                               | builds
                                                               v
                                                    +--------------------+
                                                    |      Bicycle       |
                                                    +--------------------+
                                                    | -frame             |
                                                    | -gearSystem        |
                                                    | -tireType          |
                                                    +--------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product** | `Bicycle` | Represents the fully assembled bicycle with frame, gears, and tires. |
| **Builder Interface** | `BicycleBuilder` | Outlines the assembly methods: `buildFrame`, `buildGearSystem`, `buildTireType`, and `getBicycle`. |
| **Concrete Builders** | `CommuterBuilder`, `MountainBeastBuilder` | Assembles parts specifically suited for commuter or mountain biking. |
| **Director** | `BicycleFactoryDirector` | Controls the step-by-step assembly process (`constructBicycle()`). |
| **Client** | `C1_BicycleBuilder` | Supplies the builder to the director and outputs the completed bike. |

---

## 3. Solution Walkthrough

1. **Product (`Bicycle`)**: Encapsulates components and includes custom setters and a readable `toString()` representation.
2. **Builder Interface (`BicycleBuilder`)**: Declares granular part-construction methods.
3. **Model Implementations**:
   - `CommuterBuilder`: Sets Aluminum Frame, Single Speed Gear, and Road Tires.
   - `MountainBeastBuilder`: Sets Carbon Fiber Frame, 12-Speed Gear, and Off-road Grip Tires.
4. **Director (`constructBicycle`)**: Orchestrates the assembly:
   ```java
   builder.buildFrame();
   builder.buildGearSystem();
   builder.buildTireType();
   return builder.getBicycle();
   ```

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac C1_BicycleBuilder.java
java C1_BicycleBuilder
```

### Sample Output
```
Bicycle [Frame=Aluminum Frame, Gear System=Single Speed Gear, Tire Type=Road Tires]
Bicycle [Frame=Carbon Fiber Frame, Gear System=12-Speed Gear, Tire Type=Off-road Grip Tires]
```
