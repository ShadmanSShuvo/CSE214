# Creational Design Patterns

Creational design patterns abstract the instantiation process. They help make a system independent of how its objects are created, composed, and represented.

---

## 🗂️ Catalog Index

| Pattern | Primary Intent | Key Concepts | Directory |
| :--- | :--- | :--- | :--- |
| **[Singleton](Singleton/)** | Ensure a class has only one instance and provide a global point of access to it. | Private constructor, static instance holder, thread safety (`synchronized`, Double-Checked Locking, Bill Pugh, Enum). | [`Singleton/`](Singleton/) |
| **[Abstract Factory](AbstractFactory/)** | Provide an interface for creating families of related or dependent objects without specifying their concrete classes. | Abstract product interfaces, concrete factory families, compile-time product consistency. | [`AbstractFactory/`](AbstractFactory/) |
| **[Builder](Builder/)** | Separate the construction of a complex object from its representation so that the same construction process can create different representations. | Step-by-step construction, method chaining (fluent interface), immutable object construction, telescoping constructor prevention. | [`Builder/`](Builder/) |
| **[Factory Method](Factory/)** | Define an interface for creating an object, but let subclasses decide which class to instantiate. | Creator class, product interface, decoupling client code from concrete implementations. | [`Factory/`](Factory/) |

---

## ⚖️ Creational Pattern Decision Guide

```
Do you need to create...
│
├── Exactly ONE instance globally?
│   └── ➤ Use SINGLETON
│
├── A complex object with many optional parameters / steps?
│   └── ➤ Use BUILDER
│
├── A single product whose exact class is determined at runtime?
│   └── ➤ Use FACTORY METHOD
│
└── A FAMILY of related/compatible products working together?
    └── ➤ Use ABSTRACT FACTORY
```
