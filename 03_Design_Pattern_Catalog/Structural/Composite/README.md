# Composite Pattern

## 📌 Intent
Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.

---

## 🏗️ Architecture & Class Diagram

```
                         +-----------------------+
                         |     <<Component>>     |
                         |   OrganizationUnit    |
                         +-----------------------+
                         | +getSalaryCost()      |
                         | +printStructure(...)  |
                         +-----------------------+
                                     ▲
                                     │
                    +----------------+----------------+
                    │                                 │
         +---------------------+           +---------------------+
         |      <<Leaf>>       |           |    <<Composite>>    |
         |      Employee       |           |     Department      |
         +---------------------+           +---------------------+
         | - salary: double    |           | - units: List<Unit> |
         | +getSalaryCost()    |           | +add(unit: Unit)    |
         +---------------------+           | +getSalaryCost()    |
                                           +---------------------+
                                                      │ aggregates
                                                      └───────────────┘
```

---

## 💡 Key Architectural Properties

1. **Recursive Uniformity:** Clients can query `.getSalaryCost()` or `.display()` on an individual employee or on an entire corporate division containing nested sub-departments; the operation propagates down the tree transparently.
2. **Transparent vs. Safe Interface:**
   - *Transparency:* Declaring `add()` and `remove()` in the base component makes leaves and composites indistinguishable, but leaves must throw exceptions on unsupported methods.
   - *Safety (Used here):* Defining child-management operations (`add()`, `remove()`) only in the `Composite` class prevents invalid operations on leaf nodes at compile-time.

---

## 📁 Implementations in this Directory

| File | Scenario | Key Elements |
| :--- | :--- | :--- |
| **`CompositeDemo.java`** | Hierarchical File System (`FileSystemItem`, `File`, `Folder`) | Recursive file size computation and tree indentation rendering. |
| **`CompositeComplexDemo.java`** | Corporate Enterprise Hierarchy (`OrganizationUnit`, `Employee`, `Department`) | Aggregated department payroll calculation and organizational chart generation. |

---

## 🚀 How to Compile & Run
```bash
# File System Hierarchy Demo
javac CompositeDemo.java
java CompositeDemo

# Corporate Organizational Structure Demo
javac CompositeComplexDemo.java
java CompositeComplexDemo
```
