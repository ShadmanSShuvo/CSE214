# Problem Statement: Smart Home Automation Hub

**Batch**: CSE22
**Source**: `Behavioral_Online_Questions.pdf` (Subsection: B2)
**Time**: 20 minutes
**Design Pattern**: **Mediator Pattern**

---

## 1. Problem Description

You are developing a **Smart Home Automation Hub**. You have various devices:
- A **Light Sensor**
- **Automatic Blinds**
- An **Air Conditioner**

To keep the system organized, the devices must **not talk to each other directly**. Instead, they all report to the **Central Hub**:
1. When the **Light Sensor** detects **“High Brightness,”** it notifies the Hub. The Hub then tells the **Blinds** to close.
2. When the **Blinds** close, they notify the Hub. The Hub then tells the **Air Conditioner** to turn on because the room will get stuffy.

---

## 2. Design Pattern Justification: Mediator Pattern

- **Why Mediator Pattern?**:
  Without a mediator, devices would maintain direct cross-references (`LightSensor` references `AutomaticBlinds`, `AutomaticBlinds` references `AirConditioner`), creating tight coupling and circular dependencies.
- With the **Mediator Pattern**, devices only know about their central `HomeHub`. Complex interaction logic, triggers, and sequences are centralized in `SmartHomeHub`, making individual devices reusable, independent, and easily testable.

---

## 3. Architecture & Interaction Flow

```
+---------------+        "High Brightness"        +----------------+
|  LightSensor  | ----------------------------->  |                |
+---------------+                                 |                |
                                                  |                |
+---------------+          order to close         |   Central      |
| Automatic     | <-----------------------------  |   Home Hub     |
| Blinds        | ----------------------------->  |   (Mediator)   |
+---------------+         "Blinds Closed"         |                |
                                                  |                |
+---------------+          order turn on          |                |
| AirConditioner| <-----------------------------  |                |
+---------------+                                 +----------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE22/SmartHomeAutomationHub
javac *.java
java Main
```
