# Decorator Pattern

## 📌 Intent
Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

---

## 🏗️ Architecture & Class Diagram

```
                       +-----------------------+
                       |     <<Component>>     |
                       |     Notification      |
                       +-----------------------+
                       | +send(t, m): void     |
                       +-----------------------+
                                   ▲
                                   │
                  +----------------+----------------+
                  │                                 │
       +-----------------------+       +-------------------------+
       |   EmailNotification   |       |  NotificationDecorator  |
       +-----------------------+       +-------------------------+
       | +send(t, m): void     |       | - wrappee: Notification |
       +-----------------------+       | +send(t, m): void       |
                                       +-------------------------+
                                                    ▲
                                                    │
                                +-------------------+-------------------+
                                │                                       │
                    +-----------------------+               +-----------------------+
                    |  EncryptionDecorator  |               |  TimestampDecorator   |
                    +-----------------------+               +-----------------------+
                    | +send(t, m): void     |               | +send(t, m): void     |
                    +-----------------------+               +-----------------------+
```

---

## 💡 Key Architectural Properties

1. **Recursive Wrapping:** Because a decorator implements the same interface as the wrapped object, decorators can wrap other decorators indefinitely:
   ```java
   Notification secureLoggedNotif = new TimestampDecorator(
       new LoggingDecorator(
           new EncryptionDecorator(
               new EmailNotification()
           )
       )
   );
   secureLoggedNotif.send("Alert", "Server Down");
   ```
2. **Open/Closed Principle:** New capabilities (e.g. Compression, Retry) can be added as standalone decorators without modifying existing components or previous decorators.
3. **Decorator vs. Subclassing:** Subclassing adds behavior statically at compile-time and affects all instances of the subclass. Decorators add behavior dynamically at runtime on a per-instance basis.

---

## 📁 Implementations in this Directory

| File | Scenario | Key Features |
| :--- | :--- | :--- |
| **`DecoratorDemo.java`** | Beverage Cost & Description Customizer (`Coffee`, `Milk`, `Sugar`, `Whip`) | Classical beverage ordering scenario demonstrating dynamic additive cost calculations. |
| **`DecoratorComplexDemo.java`** | Multi-Layer Enterprise Notification Pipeline | Stacking encryption (AES-style payload scrambling), timestamp injection, priority tagging, and audit logging onto notification channels. |

---

## 🚀 How to Compile & Run
```bash
# Basic Coffee Order Decorator
javac DecoratorDemo.java
java DecoratorDemo

# Enterprise Multi-Layer Notification Decorator
javac DecoratorComplexDemo.java
java DecoratorComplexDemo
```
