# Batch 23 - Section C1: Examination System Audit Logger

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns (January 2026)
- **Pattern:** Singleton Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing an online examination system. All modules, such as **Student Login**, **Question Management**, and **Result Processing**, must record their activities using one shared `AuditLogger`.

Creating multiple logger objects could produce inconsistent logs and multiple fragmented output files.

**Task:** Implement the `AuditLogger` class so that only one instance can exist throughout the application:
- The logger constructor must not be directly accessible from client classes.
- The logger should be created when it is requested for the first time (lazy initialization).
- Demonstrate access from two different modules.
- Show that both modules receive the exact same logger instance.
- Thread synchronization is not required.

---

## 2. Design Pattern & Architecture

### Why Singleton?
An examination audit trail requires a single centralized sink for recording chronological events across the entire test session. The **Singleton Pattern** restricts instantiation to a single shared object and provides a global access method.

```
+-------------------------------------------------+
|                   AuditLogger                   |
+-------------------------------------------------+
| - instance: AuditLogger                         |
+-------------------------------------------------+
| - AuditLogger()                                 | <-- private constructor
| + getInstance(): AuditLogger                    | <-- global access point
| + log(message: String): void                    |
+-------------------------------------------------+
                         ^
        +----------------+----------------+
        |                                 |
+--------------------+         +--------------------------+
| StudentLoginModule |         | QuestionManagementModule |
+--------------------+         +--------------------------+
| - logger           |         | - logger                 |
| + login(...)       |         | + addQuestion(...)       |
+--------------------+         +--------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Singleton** | `AuditLogger` | Private constructor, static instance variable, and `getInstance()` lazy accessor. |
| **Client Module 1** | `StudentLoginModule` | Acquires logger reference and logs student authentication events. |
| **Client Module 2** | `QuestionManagementModule` | Acquires logger reference and logs question modification/publish events. |
| **Client / Driver** | `C1Main` | Demonstrates multi-module access and verifies object identity (`==`). |

---

## 3. Solution Walkthrough

1. **Private Constructor**: Declaring `private AuditLogger()` ensures that client classes attempting `new AuditLogger()` will trigger a compile error.
2. **Lazy Initialization**:
   ```java
   public static AuditLogger getInstance() {
       if (instance == null) {
           instance = new AuditLogger();
       }
       return instance;
   }
   ```
   The instance is instantiated only when first called by any module.
3. **Multi-Module Demonstration**:
   - `StudentLoginModule` requests `AuditLogger.getInstance()` and logs `"User 'john_doe' logged in successfully."`
   - `QuestionManagementModule` requests `AuditLogger.getInstance()` and logs `"Question ID Q101 was added to the exam."`
4. **Reference Verification**:
   ```java
   AuditLogger loggerFromLogin = loginModule.getLogger();
   AuditLogger loggerFromQuestions = questionModule.getLogger();
   if (loggerFromLogin == loggerFromQuestions) { ... }
   ```
   Both references point to the exact same memory address (identical hash code `System.identityHashCode`).

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac C1Main.java
java C1Main
```

### Sample Output
```
--- Starting Examination System ---
AuditLogger initialized for the first time.
[LOG] User 'john_doe' logged in successfully.

[LOG] Question ID Q101 was added to the exam.

--- Verifying Singleton Instance ---
SUCCESS: Both modules are using the EXACT SAME logger instance.
Module 1 Logger Hashcode: 1933863327
Module 2 Logger Hashcode: 1933863327
```
