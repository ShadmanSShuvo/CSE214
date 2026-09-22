# Structural Design Patterns

Structural design patterns are concerned with how classes and objects are composed to form larger structures. Structural class patterns use inheritance to compose interfaces or implementations, while structural object patterns describe ways to compose objects to realize new functionality.

---

## 🗂️ Catalog Index

| Pattern | Primary Intent | Key Problem Solved | Subfolder |
| :--- | :--- | :--- | :--- |
| **[Adapter](Adapter/)** | Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces. | Incompatible 3rd-party APIs, legacy protocol integration, data format mismatch. | [`Adapter/`](Adapter/) |
| **[Bridge](Bridge/)** | Decouple an abstraction from its implementation so that the two can vary independently. | $M \times N$ Cartesian product explosion of subclasses when combining multiple independent dimensions. | [`Bridge/`](Bridge/) |
| **[Composite](Composite/)** | Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly. | Complex hierarchical structures (directories, UI trees, nested menus) requiring recursive operations. | [`Composite/`](Composite/) |
| **[Decorator](Decorator/)** | Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality. | Explosion of subclasses to support arbitrary combinations of optional features. | [`Decorator/`](Decorator/) |

---

## 🔬 Quick Architectural Contrast

```
                       Structural Pattern Distinctions
                       
    Interface Transformation                 Implementation Separation
    ┌─────────────────────────┐              ┌─────────────────────────┐
    │ Adapter:                │              │ Bridge:                 │
    │ Makes incompatible APIs │              │ Separates Abstraction  │
    │ work together           │              │ from Implementation     │
    └─────────────────────────┘              └─────────────────────────┘
    
    Part-Whole Hierarchy                     Dynamic Feature Extension
    ┌─────────────────────────┐              ┌─────────────────────────┐
    │ Composite:              │              │ Decorator:              │
    │ Treats leaf and branch  │              │ Transparently wraps     │
    │ nodes uniformly         │              │ objects with features   │
    └─────────────────────────┘              └─────────────────────────┘
```
