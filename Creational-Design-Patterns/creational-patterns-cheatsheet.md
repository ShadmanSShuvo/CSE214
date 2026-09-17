# Creational Design Patterns Cheatsheet

## Quick Decision Guide

| Signal in the problem | Pattern |
|---|---|
| One product interface, pick **one** concrete class based on a type/string | **Factory Method** |
| **Multiple related products** (families) that must be created together and stay consistent | **Abstract Factory** |
| One complex object built from **several parts/steps**, same steps → different results | **Builder** |
| Must guarantee **exactly one instance** exists, shared everywhere | **Singleton** |

---

## 1. Factory Method

### Intent
Delegate object creation to a method, so the client depends only on an interface, not concrete classes.

### Structure
```
Product (interface)
   ▲
   ├── ConcreteProductA
   └── ConcreteProductB

Creator / Factory
   + createProduct(type): Product   // decides which concrete class to instantiate
```

### When to apply
- You have **one interface**, multiple implementations, and select one based on input (a string, enum, file extension, etc.).
- Adding a new variant later should only need: one new class + one new `case`.
- Client code should **never** say `new ConcreteProductX()` directly.

### Keywords in problem statements
"recognize type and use appropriate processor", "add new types in future without modifying client code", "client shouldn't know concrete class names".

### Example use cases
- Document processor per file extension (.pdf/.docx/.txt)
- Transport object per delivery mode ("Road"/"Sea")
- Notification channel per string ("SMS"/"Email")
- Payment method per type ("CreditCard"/"PayPal")

### Java Skeleton
```java
interface Product {
    void action();
}

class ConcreteProductA implements Product {
    public void action() { System.out.println("A"); }
}

class ConcreteProductB implements Product {
    public void action() { System.out.println("B"); }
}

class ProductFactory {
    public static Product create(String type) {
        switch (type) {
            case "A": return new ConcreteProductA();
            case "B": return new ConcreteProductB();
            default: throw new IllegalArgumentException("Unknown: " + type);
        }
    }
}
```

---

## 2. Abstract Factory

### Intent
Create **families of related objects** without specifying their concrete classes, guaranteeing the objects in a family are compatible with each other.

### Structure
```
AbstractFactory (interface)
   + createProductA(): ProductA
   + createProductB(): ProductB
        ▲
        ├── ConcreteFactory1  → creates ProductA1, ProductB1  (family 1)
        └── ConcreteFactory2  → creates ProductA2, ProductB2  (family 2)

ProductA (interface)        ProductB (interface)
   ▲                            ▲
   ├── ProductA1                ├── ProductB1
   └── ProductA2                └── ProductB2
```

### When to apply
- There are **two or more distinct product types** (e.g., Processor + Display, Letter + Resume), each with **multiple variants**.
- Variants must be picked **together, consistently** — e.g., a "WorkPro" always gets Xeon + IPS, never Xeon + OLED.
- You're choosing a **"family"/"mode"/"theme"** upfront, and everything created afterward must match it.

### Keywords in problem statements
"two companies, one makes X, other makes Y", "select a model and system creates matching components", "mode: professional vs informal, then create multiple kinds of objects in that mode".

### Example use cases
- Computer models (Processor + Display pairs: WorkPro vs LiteMax)
- UI themes (Button + Checkbox: Dark vs Light)
- Document creator (Letter + Resume: Formal vs Informal)

### Java Skeleton
```java
interface ProductA { String describe(); }
interface ProductB { String describe(); }

class ProductA1 implements ProductA { public String describe() { return "A1"; } }
class ProductB1 implements ProductB { public String describe() { return "B1"; } }
class ProductA2 implements ProductA { public String describe() { return "A2"; } }
class ProductB2 implements ProductB { public String describe() { return "B2"; } }

interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}

class ConcreteFactory1 implements AbstractFactory {
    public ProductA createProductA() { return new ProductA1(); }
    public ProductB createProductB() { return new ProductB1(); }
}

class ConcreteFactory2 implements AbstractFactory {
    public ProductA createProductA() { return new ProductA2(); }
    public ProductB createProductB() { return new ProductB2(); }
}
```

### Factory Method vs Abstract Factory — tell them apart
| | Factory Method | Abstract Factory |
|---|---|---|
| Products created | 1 per call | Multiple related products per factory |
| Consistency concern | None | Must stay compatible within a family |
| Typical clue | "pick one of these classes" | "pick one of these classes **and** one of those classes, matched" |

---

## 3. Builder

### Intent
Separate the **construction** of a complex object from its **representation**, so the same construction process can produce different representations.

### Structure
```
Builder (interface)
   + buildPartA()
   + buildPartB()
   + buildPartC()
   + getResult(): Product
        ▲
        ├── ConcreteBuilder1
        └── ConcreteBuilder2

Director
   + construct(builder): Product
       calls buildPartA(), buildPartB(), buildPartC() in order

Product
   - partA, partB, partC (set via setters, no public multi-arg constructor needed)
```

### When to apply
- The object has **several components/steps** (3+ fields typically), and construction is step-by-step.
- The problem explicitly separates "construction process" from "final representation".
- Same sequence of steps → different concrete builder → different final object.
- Explicitly says you can use Strings/simple fields (no complex sub-objects needed) — a common simplification in course assignments.

### Keywords in problem statements
"consists of multiple components", "construction process involves multiple steps", "separate construction from representation", "step-by-step (build X, then Y, then Z)".

### Example use cases
- Holiday package (Flight + Hotel + Activity) → Relaxation vs Adventure
- Bicycle (Frame + Gears + Tires) → Commuter vs Mountain Beast
- Meal plan (Starter + Main + Dessert) → Bengali vs Chinese

### Java Skeleton
```java
class Product {
    private String partA, partB, partC;
    public void setPartA(String v) { partA = v; }
    public void setPartB(String v) { partB = v; }
    public void setPartC(String v) { partC = v; }
    public String toString() { return partA + ", " + partB + ", " + partC; }
}

interface Builder {
    void buildPartA();
    void buildPartB();
    void buildPartC();
    Product getResult();
}

class ConcreteBuilder1 implements Builder {
    private Product product = new Product();
    public void buildPartA() { product.setPartA("A1"); }
    public void buildPartB() { product.setPartB("B1"); }
    public void buildPartC() { product.setPartC("C1"); }
    public Product getResult() { return product; }
}

class Director {
    private Builder builder;
    public Director(Builder builder) { this.builder = builder; }
    public void setBuilder(Builder builder) { this.builder = builder; }
    public Product construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
```

---

## 4. Singleton

### Intent
Ensure a class has **only one instance**, and provide a **global access point** to it.

### Structure
```
Singleton
   - static instance: Singleton
   - private constructor()
   + static getInstance(): Singleton
```

### When to apply
- Problem explicitly forbids multiple instances ("must not have more than one instance", "same shared object", "prevent inconsistent state/duplicate copies").
- A resource that's **expensive to create** and should be loaded/initialized once (config file, DB connection, log file handle).
- Multiple independent modules/clients need to access the **same shared state**.

### Keywords in problem statements
"single instance", "shared across the application", "loading is expensive, load once", "prevent multiple copies causing inconsistency".

### Example use cases
- Logger (single log file / audit trail)
- Game/App configuration manager
- Database connection pool

### Java Skeleton
```java
class Singleton {
    private static Singleton instance;

    private Singleton() {
        // expensive init, e.g. load from disk
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

> Note: this lazy version isn't thread-safe. For thread-safety, add `synchronized` to `getInstance()`, or use an eagerly-initialized `private static final Singleton instance = new Singleton();`. For coursework this basic version is usually sufficient.

---

## One-Page Summary Table

| Pattern | Solves | Core mechanism | Ask yourself |
|---|---|---|---|
| **Factory Method** | Which one class to instantiate | A method with a switch/if returning the interface type | "Am I picking **one** of several interchangeable classes?" |
| **Abstract Factory** | Which **matched set** of classes to instantiate | A factory interface producing multiple related products | "Do I have **2+ product types**, each with variants, that must match?" |
| **Builder** | How to assemble a complex object step-by-step | A builder interface + optional Director calling build steps in order | "Does the object have **multiple parts/steps**, needing separation of construction vs. representation?" |
| **Singleton** | How to guarantee one shared instance | Private constructor + static `getInstance()` | "Must this **never** have more than one instance?" |
