# Online 2 (Structural Patterns): Batch 22

**Batch:** CSE 22 (BUET)
**Exam Focus:** Structural Design Patterns

---

## 📋 Problem & Solution Index

| Section | Domain Scenario | Applied Pattern | Directory |
| :---: | :--- | :--- | :--- |
| **A1** | IoT Security Device: Configurable notification decorators (AES Encryption, Priority tagging, Persistent Logging). | **Decorator** | [`A1_IoTSecurityNotifierDecorator/`](A1_IoTSecurityNotifierDecorator/) |
| **A2** | ZBazar Grocery Bundles: Individual groceries and nested recurring subscription baskets with aggregate pricing. | **Composite** | [`A2_ZBazarGroceryComposite/`](A2_ZBazarGroceryComposite/) |
| **B1** | Dlachal Grocery Alerts: Event notifications (Confirmed, Dispatched, Failed) across communication channels (Email, SMS, WhatsApp). | **Bridge** | [`B1_DlachalNotificationBridge/`](B1_DlachalNotificationBridge/) |
| **B2** | Smart Home App: Unifying control of 3rd-party `OldSmartBulb` and `LegacyHeater` through standard smart device interfaces. | **Adapter** | [`B2_SmartHomeDeviceAdapter/`](B2_SmartHomeDeviceAdapter/) |
| **C1** | ZBazar Ramadan Packages: Seasonal hampers decorated with Fresh Fruit, Traditional Sweets, and Premium Gift Wrapping add-ons. | **Decorator** | [`C1_RamadanPackageDecorator/`](C1_RamadanPackageDecorator/) |
| **C2** | ZBazar Logistics: Decoupling delivery tiers (Standard vs. Express) from transportation fleets (Bike, Delivery Van, Drone, Delivery Robot). | **Bridge** | [`C2_ZBazarDeliveryBridge/`](C2_ZBazarDeliveryBridge/) |

---

## ⚡ How to Compile & Run
```bash
# Example: Section B1 Notification Bridge
cd B1_DlachalNotificationBridge
javac *.java
java Main
```
