# Gang of Four (GoF) Design Pattern Catalog

A curated, production-ready reference encyclopedia of **Object-Oriented Design Patterns** in Java, structured for **BUET CSE 214**.

This catalog features theoretical foundations, architectural UML diagrams, clean reference implementations, complex real-world enterprise scenarios, and exam cheat sheets.

---

## 🏛️ Catalog Architecture

```
03_Design_Pattern_Catalog/
├── Creational/                     # Patterns dealing with object creation mechanisms
│   ├── Singleton/                  # Ensures a class has only one instance with global access
│   ├── AbstractFactory/            # Creates families of related or dependent objects
│   ├── Builder/                    # Constructs complex objects step-by-step
│   └── Factory/                    # Defines an interface for creating an object, subclasses decide class
│
├── Structural/                     # Patterns dealing with class and object composition
│   ├── Adapter/                    # Converts the interface of a class into another expected interface
│   ├── Bridge/                     # Decouples an abstraction from its implementation
│   ├── Composite/                  # Composes objects into tree structures to represent part-whole hierarchies
│   └── Decorator/                  # Attaches additional responsibilities to an object dynamically
│
├── Behavioral/                     # Patterns dealing with object interaction and responsibilities
│   ├── Patterns/                   # Modular pattern implementations (Observer, State, Command, Mediator, Template, Strategy)
│   ├── AdaptiveSorting/            # Dynamic runtime algorithm selection using Strategy Pattern
│   └── standalone_demos/           # Single-file runnable demos for immediate review
│
└── Study_Guides_and_CheatSheets/   # Revision guides, pattern recognition frameworks, and practice sets
```

---

## 📚 Pattern Taxonomy & Comparison Matrix

### 1. Creational Patterns
| Pattern | Primary Intent | Key Problem Solved | When to Use |
| :--- | :--- | :--- | :--- |
| **[Singleton](Creational/Singleton/)** | Ensure a class has only one instance, providing a global point of access. | Resource contention, uncoordinated state modification. | Database connection pools, central logger, thread pools, global configuration. |
| **[Factory Method](Creational/Factory/)** | Define an interface for creating an object, but let subclasses decide which class to instantiate. | Tight coupling between creator and concrete products. | Frameworks where component types vary by deployment or platform. |
| **[Abstract Factory](Creational/AbstractFactory/)** | Provide an interface for creating families of related or dependent objects without specifying concrete classes. | Ensuring mutually compatible product suites. | Multi-OS UI toolkits (Windows vs Mac buttons/windows), multi-brand hardware assembly. |
| **[Builder](Creational/Builder/)** | Separate the construction of a complex object from its representation. | Telescoping constructors, mutable incomplete objects. | Complex domain entities with 4+ optional parameters, immutable record construction. |

---

### 2. Structural Patterns
| Pattern | Primary Intent | Key Problem Solved | When to Use |
| :--- | :--- | :--- | :--- |
| **[Adapter](Structural/Adapter/)** | Convert the interface of a class into another interface clients expect. | Interface incompatibility between caller and callee. | Integrating legacy code, wrapping 3rd-party payment gateways or API clients. |
| **[Bridge](Structural/Bridge/)** | Decouple an abstraction from its implementation so the two can vary independently. | Cartesian explosion of subclasses along orthogonal axes. | Graphic drawing APIs across OS engines, messaging channels across notification types. |
| **[Composite](Structural/Composite/)** | Compose objects into tree structures to represent part-whole hierarchies. | Uniformity between individual leaves and compound containers. | File systems, GUI widget hierarchies, nested product bundles (meals/groceries). |
| **[Decorator](Structural/Decorator/)** | Attach additional responsibilities to an object dynamically as an alternative to subclassing. | Rigid, static feature explosion through multiple inheritance. | I/O streams (`BufferedInputStream`), pizza toppings, runtime device upgrades. |

---

### 3. Behavioral Patterns
| Pattern | Primary Intent | Key Problem Solved | When to Use |
| :--- | :--- | :--- | :--- |
| **[Observer](Behavioral/Patterns/Observer/)** | Define a one-to-many dependency so that when one object changes state, all dependents are notified. | Polling overhead, tight coupling between event source and listeners. | Stock tickers, real-time alert broadcasts, UI event listeners. |
| **[State](Behavioral/Patterns/State/)** | Allow an object to alter its behavior when its internal state changes. | Massive `switch` / `if-else` state condition blocks. | Finite state machines: order delivery lifecycles, connection states. |
| **[Strategy](Behavioral/Patterns/Strategy/)** | Define a family of algorithms, encapsulate each one, and make them interchangeable. | Hardcoding algorithmic variations inside business classes. | Sorting strategies, route planning, discount calculation policies. |
| **[Mediator](Behavioral/Patterns/Mediator/)** | Define an object that encapsulates how a set of objects interact. | High coupling ($O(N^2)$ direct connections) among collaborating classes. | Air traffic control towers, hospital emergency coordination, chat rooms. |
| **[Template Method](Behavioral/Patterns/TemplateMethod/)** | Define the skeleton of an algorithm in an operation, deferring some steps to subclasses. | Code duplication across algorithmic variants with identical invariant steps. | Data parsers (CSV, XML, JSON), test harnesses, game loops. |
| **[Command](Behavioral/Patterns/Command/)** | Encapsulate a request as an object, thereby letting you parameterize clients with different requests. | Coupling between invoker and receiver; lack of undo/redo. | GUI button actions, remote controls, macro queues. |

---

## 🔬 Key Architectural Distinctions

```
                       Design Pattern Distinctions

    Structural Wrapping                      Creational Selection
    ┌──────────────────────┐                 ┌──────────────────────┐
    │ Adapter:             │                 │ Factory Method:      │
    │ Changes interface    │                 │ 1 method, 1 product  │
    ├──────────────────────┤                 ├──────────────────────┤
    │ Decorator:           │                 │ Abstract Factory:    │
    │ Keeps interface,     │                 │ Factory of factories │
    │ adds behavior        │                 │ (family of products) │
    ├──────────────────────┤                 ├──────────────────────┤
    │ Proxy:               │                 │ Builder:             │
    │ Keeps interface,     │                 │ Step-by-step multi-  │
    │ controls access      │                 │ attribute assembly   │
    └──────────────────────┘                 └──────────────────────┘
```

---

## 📖 Study Guides & Exam Preparation

Navigate to [`Study_Guides_and_CheatSheets/`](Study_Guides_and_CheatSheets/) for:
- Comprehensive Markdown study guides with real-world scenarios.
- Behavioral & Creational quick reference sheets.
- Pattern recognition guides for identifying the correct pattern under exam pressure.
- Practice question sets with model solutions.
