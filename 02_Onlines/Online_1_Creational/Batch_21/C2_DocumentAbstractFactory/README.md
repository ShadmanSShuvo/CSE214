# Batch 21 - Section C2: Multi-Style Document Generation

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Abstract Factory Pattern
- **Language:** Java

---

## 1. Problem Statement

You are designing a system to generate documents in different styles:
- Document Types: **Letters** and **Resumes**.
- Style Modes: **Formal** (professional and formal) and **Informal** (casual).

The client selects their preferred mode (Formal or Informal). After that, they can create letters or resumes from that mode in their preferred style. Classes representing document creators must return objects of letters or resumes appropriately.

---

## 2. Design Pattern & Architecture

### Why Abstract Factory?
The system features two independent product families (`Letter` and `Resume`) that must remain stylistically consistent across two variations (`Formal` and `Informal`). The **Abstract Factory Pattern** ensures that a client operating in `Formal` mode cannot accidentally produce an informal letter alongside a formal resume.

```
          +-----------------------+
          |    DocumentCreator    |
          +-----------------------+
          | +createLetter()       |
          | +createResume()       |
          +-----------------------+
                      ^
          +-----------+-----------+
          |                       |
+-----------------------+ +-------------------------+
| FormalDocumentCreator | | InformalDocumentCreator |
+-----------------------+ +-------------------------+
| creates:              | | creates:                |
| - FormalLetter        | | - InformalLetter        |
| - FormalResume        | | - InformalResume        |
+-----------------------+ +-------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Abstract Product A** | `Letter` | Interface defining `getStyle()` for letters. |
| **Concrete Products A** | `FormalLetter`, `InformalLetter` | Specialized letter formatting. |
| **Abstract Product B** | `Resume` | Interface defining `getStyle()` for resumes. |
| **Concrete Products B** | `FormalResume`, `InformalResume` | Specialized resume formatting. |
| **Abstract Factory** | `DocumentCreator` | Declares creation methods `createLetter()` and `createResume()`. |
| **Concrete Factories** | `FormalDocumentCreator`, `InformalDocumentCreator` | Creates matching families of styled documents. |
| **Client** | `C2_DocumentAbstractFactory` | Interacts via `DocumentCreator` without knowing concrete style classes. |

---

## 3. Solution Walkthrough

1. **Document Interfaces**:
   - `Letter`: Returns string description via `getStyle()`.
   - `Resume`: Returns string description via `getStyle()`.
2. **Concrete Products**:
   - `FormalLetter` / `FormalResume`: Describe professional tone and structured layout.
   - `InformalLetter` / `InformalResume`: Describe casual tone and relaxed layout.
3. **Factory Implementations**:
   - `FormalDocumentCreator`: Instantiates `FormalLetter` and `FormalResume`.
   - `InformalDocumentCreator`: Instantiates `InformalLetter` and `InformalResume`.
4. **Client Mode Resolution (`getDocumentCreator`)**: Helper method resolving string input into the corresponding concrete factory.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac C2_DocumentAbstractFactory.java
java C2_DocumentAbstractFactory
```

### Sample Output
```
Formal Letter (professional tone, structured layout)
Formal Resume (professional tone, structured layout)
Informal Letter (casual tone, relaxed layout)
Informal Resume (casual tone, relaxed layout)
```
