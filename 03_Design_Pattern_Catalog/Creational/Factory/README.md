# Factory Method & Simple Factory Patterns

## 📌 Intent
Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses.

---

## 🏗️ Architecture & Class Diagram

```
                 +--------------------------------+
                 |          Application           |
                 +--------------------------------+
                 | +newDocument(): void           |
                 | #createDocument(): Document    | <--- Factory Method
                 +--------------------------------+
                                 ^
                                 |
                 +---------------+----------------+
                 |                                |
     +-----------------------+        +------------------------+
     |    PDFApplication     |        |    WordApplication     |
     +-----------------------+        +------------------------+
     | +createDocument(): Doc|        | +createDocument(): Doc |
     +-----------------------+        +------------------------+
                 |                                |
                 v creates                        v creates
         +---------------+                +----------------+
         |  PDFDocument  |                |  WordDocument  |
         +---------------+                +----------------+
                 |                                |
                 +----------------+---------------+
                                  | implements
                                  v
                       +--------------------+
                       |      Document      |
                       +--------------------+
                       | +open(): void      |
                       +--------------------+
```

---

## 🔍 Progression of Factory Patterns

The `lecture_examples/` subfolder provides a pedagogical four-step evolution:
1. **`1_No_Factory/`**: Direct tight coupling with `new` statements embedded in business logic.
2. **`2_Simple_Factory/`**: Static method with `switch` statements creating concrete products (centralized but violates Open/Closed Principle upon expansion).
3. **`3_Factory_Method/`**: Subclasses override factory methods to instantiate concrete classes without modifying caller logic (satisfies Open/Closed Principle).
4. **`4_Abstract_Factory/`**: Scaling from a single product to families of interrelated products.

---

## 🚀 How to Compile & Run
```bash
# Classical GoF Demo
javac FactoryMethodDemo.java
java FactoryMethodDemo

# Python variant
python3 factory-method.py
```
