# Batch 21 - Section A2: Computer Model System

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Abstract Factory Pattern
- **Language:** Java

---

## 1. Problem Statement

You are designing a system to manage two computer models: **WorkPro** and **LiteMax**. They have differences in their Processor and Display:
- **WorkPro** is a professional workstation that uses an **Intel Xeon Processor** and **IPS Display**.
- **LiteMax** is a lightweight device that uses an **ARM Processor** and **OLED Display**.

There are two independent component supplier domains: one produces Processors and the other produces Displays.

You must implement a system where the client selects their preferred model. The system creates the computer with the appropriate family of compatible components. The classes representing computers must print descriptions showing the model name and its components.

---

## 2. Design Pattern & Architecture

### Why Abstract Factory?
There are two independent product families (`Processor` and `Display`), each with two distinct variants:
- Processors: `IntelXeonProcessor` vs `ArmProcessor`
- Displays: `IpsDisplay` vs `OledDisplay`

The **Abstract Factory Pattern** ensures that mismatched components are never combined (e.g., an Intel Xeon processor paired with an OLED display is prohibited by construction).

```
         +-----------------------+
         |  ComputerPartsFactory |
         +-----------------------+
         | +createProcessor()    |
         | +createDisplay()      |
         +-----------------------+
                     ^
         +-----------+-----------+
         |                       |
+---------------------+ +--------------------+
| WorkProPartsFactory | | LiteMaxPartsFactory|
+---------------------+ +--------------------+
| creates:            | | creates:           |
| - IntelXeonProcessor| | - ArmProcessor     |
| - IpsDisplay        | | - OledDisplay      |
+---------------------+ +--------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Abstract Product A** | `Processor` | Interface for processor components (`getDescription()`). |
| **Concrete Products A** | `IntelXeonProcessor`, `ArmProcessor` | Concrete processor implementations. |
| **Abstract Product B** | `Display` | Interface for display components (`getDescription()`). |
| **Concrete Products B** | `IpsDisplay`, `OledDisplay` | Concrete display implementations. |
| **Abstract Factory** | `ComputerPartsFactory` | Declares factory methods for creating each component family. |
| **Concrete Factories** | `WorkProPartsFactory`, `LiteMaxPartsFactory` | Assembles matching, compatible sets of components. |
| **Product Aggregate** | `Computer` | Holds the assembled components and prints specifications. |
| **Client** | `A2_ComputerAbstractFactory` | Creates computers via the selected factory without coupling to concrete parts. |

---

## 3. Solution Walkthrough

1. **Product Hierarchies**: `Processor` and `Display` interfaces define common description methods.
2. **Abstract Factory (`ComputerPartsFactory`)**:
   - `createProcessor(): Processor`
   - `createDisplay(): Display`
3. **Model Factories**:
   - `WorkProPartsFactory` returns `IntelXeonProcessor` and `IpsDisplay`.
   - `LiteMaxPartsFactory` returns `ArmProcessor` and `OledDisplay`.
4. **Assembly (`Computer`)**:
   - Constructor receives the `ComputerPartsFactory` and calls factory methods to wire the components.
   - `printDescription()` displays the model name, processor, and display characteristics.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac A2_ComputerAbstractFactory.java
java A2_ComputerAbstractFactory
```

### Sample Output
```
Model: WorkPro
  Processor: Intel Xeon Processor
  Display: IPS Display
Model: LiteMax
  Processor: ARM Processor
  Display: OLED Display
```
