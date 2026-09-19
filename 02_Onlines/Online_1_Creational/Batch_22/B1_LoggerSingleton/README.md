# Batch 22 - Section B1: Banking Application Logger

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Singleton Pattern
- **Language:** Java

---

## 1. Problem Statement

In a banking application, all operations (e.g., deposits, withdrawals, transfers) must be recorded using a central **Logger**. Creating multiple logger objects could lead to fragmented log files, inconsistent event ordering, and unnecessary resource consumption.

Implement the `Logger` class so that only a single instance can exist throughout the lifetime of the application. Demonstrate access from multiple independent modules (e.g., `DepositModule`, `WithdrawalModule`) and prove that both modules share the exact same instance in memory.

---

## 2. Design Pattern & Architecture

### Why Singleton?
A logging system represents a shared shared resource. The **Singleton Pattern** ensures:
1. Exactly one instance of `Logger` is created in memory.
2. Direct instantiation via `new Logger()` is blocked by declaring the constructor `private`.
3. A static method `getInstance()` provides a controlled global access point.

```
+---------------------------------------+
|                Logger                 |
+---------------------------------------+
| - instance: Logger                    |
| - logRecords: List<String>            |
+---------------------------------------+
| - Logger()                            | <-- private constructor
| + getInstance(): Logger               | <-- global access point
| + log(message: String): void          |
| + printAllLogs(): void                |
+---------------------------------------+
                   ^
         +---------+---------+
         |                   |
+-----------------+ +-------------------+
|  DepositModule  | | WithdrawalModule  |
+-----------------+ +-------------------+
| uses:           | | uses:             |
| Logger.get...() | | Logger.get...()   |
+-----------------+ +-------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Singleton** | `Logger` | Houses the private static instance, private constructor, and lazy accessor `getInstance()`. |
| **Client Module 1** | `DepositModule` | Records deposit transactions via `Logger.getInstance()`. |
| **Client Module 2** | `WithdrawalModule` | Records withdrawal transactions via `Logger.getInstance()`. |
| **Client / Driver** | `B1_LoggerSingleton` | Verifies reference equality (`logger1 == logger2`) and prints the full audit trail. |

---

## 3. Solution Walkthrough

1. **Private Constructor**: Prevents external classes from invoking `new Logger()`.
2. **Lazy Initialization (`getInstance`)**:
   ```java
   public static Logger getInstance() {
       if (instance == null) {
           instance = new Logger();
       }
       return instance;
   }
   ```
3. **Multi-Module Testing**:
   - `DepositModule` invokes `Logger.getInstance().log(...)`.
   - `WithdrawalModule` invokes `Logger.getInstance().log(...)`.
4. **Verification**: Reference check `logger1 == logger2` confirms both variables reference the same heap address, and `printAllLogs()` shows sequential records from both modules in the shared audit trail.

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac B1_LoggerSingleton.java
java B1_LoggerSingleton
```

### Sample Output
```
Logger instance created.
[LOG] Deposit of $500.0 processed.
[LOG] Withdrawal of $200.0 processed.
logger1 == logger2: true
---- Full Audit Trail ----
Deposit of $500.0 processed.
Withdrawal of $200.0 processed.
```
