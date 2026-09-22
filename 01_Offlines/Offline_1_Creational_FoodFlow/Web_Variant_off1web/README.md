# FoodFlow: Web Variant Implementation

This directory contains a web-adapted variant of the **FoodFlow** ordering system, tailored for web service data interchange.

---

## 🎯 Architectural Notes

- Implements fluent `Order.Builder` and `OrderItem.Builder` methods.
- Includes the full test harness (`TestHarness.java`) and baseline CSV menu catalog (`data/menu.csv`).
- Can be compiled and executed via:
  ```bash
  javac src/*.java src/*/*.java TestHarness.java
  java -cp .:src TestHarness
  ```
