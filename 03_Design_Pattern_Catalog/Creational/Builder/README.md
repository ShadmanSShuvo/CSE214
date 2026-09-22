# Builder Pattern

## 📌 Intent
Separate the construction of a complex object from its representation so that the same construction process can create different representations.

---

## 🏗️ Architecture & Structural Variations

### 1. Classical GoF Builder (with Director)
As illustrated in `BuilderDemo.java`:
```
      +-------------+              +--------------------+
      |  Director   | <>---------> |    HouseBuilder    |
      +-------------+              +--------------------+
      | +construct()|              | +buildFoundation() |
      +-------------+              | +buildWalls()      |
                                   | +buildRoof()       |
                                   | +getResult(): House|
                                   +--------------------+
                                             ^
                                             |
                                  +----------------------+
                                  |  WoodenHouseBuilder  |
                                  +----------------------+
```

### 2. Fluent Chained Builder (Effective Java Idiom)
As illustrated in `lecture_examples/3_Fluent_Builder/`:
```java
Candy candy = new CandyBuilder()
    .setName("Gummy Bears")
    .setFlavor("Cherry")
    .setSugarContent(25)
    .build();
```
- Eliminates telescoping constructor parameters.
- Maintains immutability by validating inputs in `build()` before instantiating the target object.

---

## 📁 Directory Contents & Examples

| Folder / File | Description |
| :--- | :--- |
| **`BuilderDemo.java`** | Classical GoF Builder with `Director`, `HouseBuilder`, `WoodenHouseBuilder`, and `StoneHouseBuilder`. |
| **`builder_pattern.py`** | Pythonic implementation of the Builder pattern using kwargs and chaining. |
| **`lecture_examples/1_No_Builder/`** | The anti-pattern: massive constructors with confusing positional arguments. |
| **`lecture_examples/2_Standard_Builder/`** | Basic GoF Builder implementation with separate builder methods. |
| **`lecture_examples/3_Fluent_Builder/`** | Fluent method chaining (`return this`) for clean syntax. |
| **`lecture_examples/4_Factory_And_Builder/`** | Hybrid pattern: Factory deciding which Builder to deploy. |
| **`my_practice/BuilderPattern/`** | Student practice exercise building custom confectionery objects. |

---

## 🚀 How to Compile & Run
```bash
# Classical demo
javac BuilderDemo.java
java BuilderDemo

# Fluent builder lecture example
cd lecture_examples/3_Fluent_Builder/src
javac *.java
java Main
```
