# Bridge Pattern

## 📌 Intent
Decouple an abstraction from its implementation so that the two can vary independently.

---

## 🏗️ Architecture & Class Diagram

The Bridge pattern replaces inheritance with object composition when a class varies across multiple orthogonal dimensions (e.g., Notification types $\times$ Delivery channels):

```
       +-----------------------+                    +-----------------------+
       |   <<Abstraction>>     |                    |  <<Implementation>>   |
       |     Notification      | ◇────────────────> |        Channel        |
       +-----------------------+                    +-----------------------+
       | - channel: Channel    |                    | +deliver(...): void   |
       | +send(): void         |                    +-----------------------+
       +-----------------------+                               ▲
                   ▲                                           │
                   │                               +-----------+-----------+
       +-----------+-----------+                   |           |           |
       |                       |              +---------+ +---------+ +---------+
+--------------+       +--------------+       |  Email  | |   SMS   | |  Slack  |
|  AlertNotif  |       | ReminderNotif|       +---------+ +---------+ +---------+
+--------------+       +--------------+
```

---

## 💡 Why Bridge? Preventing Cartesian Class Explosion

- **Without Bridge:** If you have 3 notification types (`Alert`, `Reminder`, `Promotion`) and 4 channels (`Email`, `SMS`, `Push`, `Slack`), standard inheritance requires $3 \times 4 = 12$ concrete classes (`EmailAlertNotification`, `SMSAlertNotification`, ...).
- **With Bridge:** You only need $3 + 4 = 7$ classes. Adding a new channel requires just 1 class instead of 3.

---

## 📁 Implementations in this Directory

| File | Scenario | Key Elements |
| :--- | :--- | :--- |
| **`BridgeDemo.java`** | Graphics Rendering Engine (`Shape` $\times$ `Renderer`) | Vector vs. Raster rendering decoupled from Circle/Rectangle shapes. |
| **`BridgeComplexDemo.java`** | Smart Notification Pipeline | Alert, Reminder, and System notifications decoupled from Email, SMS, Push, and Slack delivery channels. |
| **`BridgeComplexOptimized.java`** | High-Throughput Notification Dispatcher | Thread-safe, non-blocking queue integration over bridged communication channels. |

---

## 🚀 How to Compile & Run
```bash
# Basic Shape/Renderer Bridge
javac BridgeDemo.java
java BridgeDemo

# Smart Notification System Bridge
javac BridgeComplexDemo.java
java BridgeComplexDemo
```
