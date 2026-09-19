# Batch 22 Online 2 (A1) - IoT Security Device Notifier (Decorator Pattern)

## Problem Statement
You have installed an IoT security device in your home. Whenever an event occurs, the device sends a notification to your smartphone. The system currently supports three basic types of notifications:
1. **Email Notification**
2. **SMS Notification**
3. **Push Notification**

Each notification type delivers the same alert message using a different communication channel. The mobile app associated with the IoT device receives and displays these notifications.

The mobile app provides users with configurable options to enhance how notifications are handled:
- **Enable Encryption**: The message should be encrypted before being sent (e.g. prefixed with `[ENCRYPTED]`).
- **Priority Label**: The message should be tagged as `"High Priority"` before delivery (e.g. prefixed with `[HIGH PRIORITY]`).
- **Logging**: The notification delivery should be logged in the IoT device for record-keeping and auditing purposes.

**Task**: Choose the appropriate design pattern for sending notifications to seamlessly provide the above features to users.

---

## Design Pattern Analysis

### Pattern Applied: **Decorator Pattern**

### Why Decorator?
- **Combinatorial Feature Explosion**: Users can enable Encryption, Priority, and Logging in any combination (Encryption only, Priority + Logging, all three, etc.) across any notification channel (Email, SMS, Push).
- **Dynamic Configuration**: Decorators allow wrapping any base channel (`EmailNotifier`, `SMSNotifier`, `PushNotifier`) dynamically at runtime.
- **Single Responsibility Principle**: Encryption, Priority Tagging, and Logging logic are encapsulated in their own focused decorator classes.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Component** | `Notifier` | Interface defining `void send(String message)`. |
| **Concrete Components** | `EmailNotifier`, `SMSNotifier`, `PushNotifier` | Base channel deliverers. |
| **Decorator** | `NotifierDecorator` | Abstract class holding a `Notifier` wrappee. |
| **Concrete Decorators** | `EncryptionDecorator`, `PriorityDecorator`, `LoggingDecorator` | Pre/post-process message or log delivery. |
| **Client** | `A1Decorator` | Chains decorators dynamically to compose notification features. |

---

## Class Architecture

```
                    <<interface>>
                      Notifier
               +send(message: String)
                         ^
                         |
        +----------------+----------------+
        |                                 |
  EmailNotifier                    NotifierDecorator
  (also SMS, Push)                 -wrappee: Notifier
                                          ^
                                          |
        +-------------------+-------------+-------------------+
        |                   |                                 |
EncryptionDecorator   PriorityDecorator               LoggingDecorator
([ENCRYPTED] msg)     ([HIGH PRIORITY] msg)           (Audit log trail)
```

---

## Solution Walkthrough

1. **Base Component**:
   ```java
   interface Notifier {
       void send(String message);
   }
   class EmailNotifier implements Notifier {
       public void send(String message) { System.out.println("Email: " + message); }
   }
   ```
2. **Decorators**:
   - `EncryptionDecorator`: prefixes `"[ENCRYPTED] "` before forwarding to wrappee.
   - `PriorityDecorator`: prefixes `"[HIGH PRIORITY] "` before forwarding to wrappee.
   - `LoggingDecorator`: forwards message, then logs `"Log: notification sent - " + message`.
3. **Chaining**:
   ```java
   Notifier notifier = new LoggingDecorator(
                           new PriorityDecorator(
                               new EncryptionDecorator(
                                   new EmailNotifier())));
   notifier.send("Motion detected at front door");
   ```

---

## How to Compile & Run

```bash
cd Batch_22/A1_IoTSecurityNotifierDecorator
javac *.java
java A1Decorator
```

### Output
```
Email: [HIGH PRIORITY] [ENCRYPTED]Motion detected at front door
Log: notification sent - [HIGH PRIORITY] [ENCRYPTED]Motion detected at front door
```
