# Behavioral Design Patterns

Behavioral design patterns characterize complex control flow that's difficult to follow at runtime. They shift focus away from flow of control to let you concentrate solely on the way objects are interconnected.

---

## 🗂️ Catalog Index

```
03_Design_Pattern_Catalog/Behavioral/
├── Patterns/                   # Deep-dive modular packages for each behavioral pattern
│   ├── Observer/               # Weather station event broadcasting with statistical displays
│   ├── State/                  # Document lifecycle state machine (Draft, InReview, Published, Archived)
│   ├── Command/                # Programmable smart remote control with macro command execution
│   ├── Mediator/               # Airport air traffic control tower coordinating aircraft and runways
│   ├── TemplateMethod/         # Data ingestion pipeline (CSV, XML, JSON) with validation hooks
│   └── Strategy/               # GPS Navigation route calculation (Driving, Walking, Transit, Bicycling)
│
├── AdaptiveSorting/            # Adaptive sorting engine choosing optimal algorithms via Strategy
│   ├── ComparisonSorts.java    # InsertionSort, MergeSort, QuickSort
│   ├── NonComparisonSorts.java # CountingSort, RadixSort
│   ├── SortMaster.java         # Context orchestrator inspecting array properties
│   └── Main.java               # Empirical demonstration benchmark
│
└── standalone_demos/           # Single-file runnable demos for quick review and interview prep
    ├── ObserverDemo.java
    ├── StateDemo.java
    ├── CommandDemo.java
    ├── MediatorDemo.java
    ├── StrategyDemo.java
    └── TemplateMethodDemo.java
```

---

## ⚖️ Behavioral Pattern Comparison Matrix

| Pattern | Problem Addressed | Core Solution | Key Mechanism |
| :--- | :--- | :--- | :--- |
| **Observer** | When an object's state change requires updating unknown numbers of other objects. | Decouples publisher from subscribers. | One-to-many event notification (`notifyObservers`). |
| **State** | When an object's behavior depends heavily on its state, leading to massive `switch`/`if-else` blocks. | Extracts states into separate classes; context delegates to current state. | Polymorphic state delegation and dynamic transitions. |
| **Strategy** | When you have multiple algorithmic variations for accomplishing a single task. | Encapsulates algorithms into interchangeable classes. | Composition over inheritance; context delegates to strategy. |
| **Mediator** | When tight coupling among collaborating classes prevents reuse and clouds maintainability. | Encapsulates collective behavior into a centralized coordinator. | Colleagues only communicate via the mediator. |
| **Template Method** | When multiple classes have identical overall workflows but diverge in individual steps. | Defines invariant algorithm skeleton in superclass, abstracting variant steps. | Superclass template calls abstract/hook methods. |
| **Command** | When requests must be queued, logged, scheduled, or made undoable. | Encapsulates a method invocation into a standalone object. | Invoker triggers `command.execute()`. |

---

## 🚀 How to Run Examples

### 1. Modular Patterns
```bash
# Example: Observer Weather Station
cd Patterns/Observer
javac *.java
java Main

# Example: Mediator Airport Control Tower
cd Patterns/Mediator
javac *.java
java Main
```

### 2. Adaptive Sorting Benchmark
```bash
cd AdaptiveSorting
javac *.java
java Main
```

### 3. Standalone Single-File Demos
```bash
cd standalone_demos
javac StateDemo.java
java StateDemo
```
