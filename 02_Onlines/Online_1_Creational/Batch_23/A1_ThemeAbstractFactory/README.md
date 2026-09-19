# Batch 23 - Section A1: Cross-Platform UI Theme Framework

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns (January 2026)
- **Pattern:** Abstract Factory Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing a cross-platform user interface library. The application supports two themes: **Light Theme** and **Dark Theme**. Each theme contains three related UI components:
- A **Button**
- A **TextField**
- A **Dialog Box**

The Light Theme creates a "Light Button", "Light TextField", and "Light Dialog", while the Dark Theme creates a "Dark Button", "Dark TextField", and "Dark Dialog". The application must not mix components from different themes (e.g., a Light Button should never be paired with a Dark Dialog).

**Task:** Implement a system where the client selects a theme and receives the appropriate family of user-interface components. The client must be able to change themes dynamically without altering its rendering logic.

---

## 2. Design Pattern & Architecture

### Why Abstract Factory?
UI components belong to cohesive families (`Button`, `TextField`, `Dialog`) that must harmonize under a single visual theme (`LightTheme` vs `DarkTheme`). The **Abstract Factory Pattern** prevents theme mismatches by ensuring that a single factory generates the entire related component suite.

```
            +-----------------------+
            |         Theme         |
            +-----------------------+
            | +createButton()       |
            | +createTextField()    |
            | +createDialog()       |
            +-----------------------+
                        ^
            +-----------+-----------+
            |                       |
+----------------------+ +---------------------+
|      LightTheme      | |      DarkTheme      |
+----------------------+ +---------------------+
| creates:             | | creates:            |
| - LightButton        | | - DarkButton        |
| - LightTextField     | | - DarkTextField     |
| - LightDialog        | | - DarkDialog        |
+----------------------+ +---------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Abstract Products** | `Button`, `TextField`, `Dialog` | Interfaces for respective UI primitives (`render()`, `display()`, `show()`). |
| **Light Family** | `LightButton`, `LightTextField`, `LightDialog` | Concrete implementations styling light-themed elements. |
| **Dark Family** | `DarkButton`, `DarkTextField`, `DarkDialog` | Concrete implementations styling dark-themed elements. |
| **Abstract Factory** | `Theme` | Factory interface declaring factory methods for button, text field, and dialog. |
| **Concrete Factories** | `LightTheme`, `DarkTheme` | Produces matching component families. |
| **Client Application** | `Application` | Holds abstract references to components and delegates rendering. |
| **Driver** | `A1Main` | Demonstrates client initialization and theme switching. |

---

## 3. Solution Walkthrough

1. **Abstract Product Interfaces**:
   - `Button`: `void render()`
   - `TextField`: `void display()`
   - `Dialog`: `void show()`
2. **Component Variations**: Each theme provides concrete implementations outputting representative console messages (e.g., `"Rendering Light Button"`, `"Showing Dark Dialog Box"`).
3. **Theme Abstraction (`Theme`)**: Defines creation hooks for all three components.
4. **Client Decoupling (`Application`)**:
   ```java
   public Application(Theme theme) {
       this.button = theme.createButton();
       this.textField = theme.createTextField();
       this.dialog = theme.createDialog();
   }
   ```
   Because components are requested from the provided theme instance, mixing components from different themes is physically prevented at compile-time.
5. **Theme Switching**: The client can swap `Application` to a new `Theme` with zero modifications to the rendering flow.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac A1Main.java
java A1Main
```

### Sample Output
```
--- Client Selecting Light Theme ---
Rendering Light Button
Displaying Light Text Field
Showing Light Dialog Box

--- Client Swapping to Dark Theme ---
Rendering Dark Button
Displaying Dark Text Field
Showing Dark Dialog Box
```
