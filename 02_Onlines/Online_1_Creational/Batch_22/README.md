# Online 1 (Creational Patterns): Batch 22

**Batch:** CSE 22 (BUET)
**Exam Focus:** Creational Design Patterns

---

## 📋 Problem & Solution Index

| Section | Problem Scenario | Applied Pattern | Directory |
| :---: | :--- | :--- | :--- |
| **A1** | Logistics Transport System: Multi-modal delivery (Truck, Cargo Ship, Airplane) without exposing concrete vehicles. | **Factory Method** | [`A1_TransportFactory/`](A1_TransportFactory/) |
| **A2** | Custom Holiday Travel Package: Step-by-step assembly of vacation packages (Transport, Hotel, Activities, Flight). | **Builder** | [`A2_HolidayPackageBuilder/`](A2_HolidayPackageBuilder/) |
| **B1** | Multi-Threaded Application Logger: Centralized, synchronized logging service preventing file corruption. | **Singleton** | [`B1_LoggerSingleton/`](B1_LoggerSingleton/) |
| **B2** | Multi-Channel Notification Dispatcher: Generating Email, SMS, and Mobile Push alerts dynamically. | **Factory Method** | [`B2_NotificationFactory/`](B2_NotificationFactory/) |
| **C1** | Modular Bicycle Builder: Step-by-step assembly of custom bikes (Road, Mountain, Hybrid) with variable components. | **Builder** | [`C1_BicycleBuilder/`](C1_BicycleBuilder/) |
| **C2** | Shared Game Settings Manager: Thread-safe global configuration instance managing graphics, audio, and gameplay settings. | **Singleton** | [`C2_GameConfigSingleton/`](C2_GameConfigSingleton/) |

---

## ⚡ How to Compile & Run
```bash
# Example: Section B2 Notification Factory
cd B2_NotificationFactory
javac *.java
java Main
```
