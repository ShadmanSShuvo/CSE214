# Problem Statement: Banking Notification System

**Batch**: CSE21
**Source**: `Online 3 A2.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **Strategy Pattern**

---

## 1. Problem Description

You are tasked with creating a notification system for a banking platform that supports multiple mediums such as email, SMS, and mobile app notifications. This system must allow customers to choose their preferred notification channel for receiving alerts, such as:
- Transaction updates (incoming/outgoing)
- Low balance warnings
- Promotional offers

Customers should have the flexibility to switch their preferred notification channel dynamically without impacting the underlying logic that processes and dispatches these notifications.

The system should be designed with extensibility in mind, making it easy to integrate new channels, such as WhatsApp, voice calls, or push notifications, without requiring changes to the existing codebase (adhering to the **Open/Closed Principle**).

Each notification channel must handle its own specific formatting and sending logic independently, ensuring a clear separation of responsibilities. Despite the diversity of channels, all notifications must maintain a consistent structure and content to provide a uniform experience across different mediums.

---

## 2. Design Pattern Justification: Strategy Pattern

- **Why Strategy?**:
  The banking platform delegates the formatting and dispatching of notifications to interchangeable strategy objects (`NotificationChannel`).
  The core banking logic (`BankingPlatform` / `Customer`) remains decoupled from the specifics of how an email, SMS, or WhatsApp message is formatted and transmitted.
- **Dynamic Switching**: Customers can switch their preferred channel at runtime via `setPreferredChannel(NotificationChannel)`.
- **Extensibility**: Adding a new channel like `WhatsAppNotificationChannel` or `VoiceCallNotificationChannel` only requires implementing the `NotificationChannel` interface without touching the existing channels or banking dispatch logic.

---

## 3. Architecture & Class Diagram

```
                 +-----------------------------------+
                 |        BankingPlatform            |
                 +-----------------------------------+
                 | + dispatchAlert(customer, alert)  |
                 +-----------------------------------+
                                   |
                                   v
                 +-----------------------------------+
                 |             Customer              |
                 +-----------------------------------+
                 | - preferredChannel                |
                 | + setPreferredChannel(channel)    |
                 | + notify(notification)            |
                 +-----------------------------------+
                                   |
                                   v uses
                 +-----------------------------------+
                 |      <<interface>>                |
                 |    NotificationChannel            |
                 +-----------------------------------+
                 | + send(Notification, Customer)    |
                 +-----------------------------------+
                      ^          ^          ^       ^
                      |          |          |       |
      +---------------+          |          |       +----------------+
      |                          |          |                        |
+----------------------+ +------------------+ +--------------------+ +----------------------+
| EmailNotification    | | SmsNotification  | | MobileAppNotif.    | | WhatsAppNotification |
+----------------------+ +------------------+ +--------------------+ +----------------------+
| + send(...)          | | + send(...)      | | + send(...)        | | + send(...)          |
+----------------------+ +------------------+ +--------------------+ +----------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE21/BankingNotification
javac *.java
java Main
```
