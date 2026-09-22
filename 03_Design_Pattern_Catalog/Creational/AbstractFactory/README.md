# Abstract Factory Pattern

## 📌 Intent
Provide an interface for creating families of related or dependent objects without specifying their concrete classes.

---

## 🏗️ Architecture & Class Diagram

```
                 +---------------------------------------+
                 |              GUIFactory               |
                 +---------------------------------------+
                 | +createButton(): Button               |
                 | +createCheckbox(): Checkbox           |
                 +---------------------------------------+
                        ^                         ^
                        |                         |
         +--------------+                         +--------------+
         |                                                       |
+------------------+                                   +------------------+
|    WinFactory    |                                   |    MacFactory    |
+------------------+                                   +------------------+
| +createButton()  | --> WinButton                     | +createButton()  | --> MacButton
| +createCheckbox()| --> WinCheckbox                   | +createCheckbox()| --> MacCheckbox
+------------------+                                   +------------------+

              Button                                Checkbox
          +------------+                         +------------+
          | +render()  |                         | +render()  |
          +------------+                         +------------+
            ^        ^                             ^        ^
            |        |                             |        |
      WinButton    MacButton                 WinCheckbox    MacCheckbox
```

---

## 💡 Key Architectural Benefits

1. **Guaranteed Family Compatibility:** Clients interact with products through abstract interfaces (`Button`, `Checkbox`). A client using `WinFactory` can never accidentally instantiate a `MacCheckbox` alongside a `WinButton`.
2. **Open/Closed Principle:** Introducing a new UI theme (e.g., `LinuxFactory`, `LinuxButton`, `LinuxCheckbox`) requires zero modifications to existing client or factory code.
3. **Single Responsibility Principle:** Object creation logic is isolated from client presentation logic.

---

## 🚀 How to Compile & Run
```bash
javac AbstractFactoryDemo.java
java AbstractFactoryDemo
```
