# Batch 22 - Section B2: Notification Service

- **Course:** CSE 214: Software Engineering & Object-Oriented Design Patterns
- **Pattern:** Factory Method Pattern
- **Language:** Java

---

## 1. Problem Statement

You are developing a notification library that supports sending messages through various channels:
- **SMS:** Sends text alerts to mobile numbers.
- **Email:** Sends formatted email notifications.
- **Push:** Dispatches mobile push notifications.

The client application passes a channel identifier string (e.g., `"SMS"`, `"Email"`, `"Push"`) and receives the corresponding notification object. The client should never directly reference or instantiate concrete notification classes. The design must accommodate new notification channels (such as Slack or WhatsApp) with minimal changes.

---

## 2. Design Pattern & Architecture

### Why Factory Method?
The **Factory Method Pattern** abstracts the instantiation of channel-specific notification handlers. The client interacts exclusively with the `Notification` interface and delegates channel resolution to `NotificationFactory`.

```
           +--------------------+
           |    Notification    | <------------------------+
           +--------------------+                          |
           | +notifyUser()      |                          |
           +--------------------+                          |
                     ^                                     |
       +-------------+-------------+                       | creates
       |             |             |                       |
+-------------+ +---------------+ +------------------+     |
| SMSNotif... | | EmailNotif... | | PushNotification |     |
+-------------+ +---------------+ +------------------+     |
                                                           |
                                  +------------------------------+
                                  |     NotificationFactory      |
                                  +------------------------------+
                                  | +createNotification(channel) |
                                  +------------------------------+
```

### Class Roles
| Role | Component | Responsibility |
|---|---|---|
| **Product Interface** | `Notification` | Common contract with `notifyUser()` method. |
| **Concrete Products** | `SMSNotification`, `EmailNotification`, `PushNotification`, `SlackMessageNotification` | Concrete channel-specific alert implementations. |
| **Creator / Factory** | `NotificationFactory` | Encapsulates channel string resolution and object creation logic. |
| **Client** | `B2_NotificationFactory` | Dispatches notifications without knowing concrete class names. |

---

## 3. Solution Walkthrough

1. **Abstraction**: `Notification` interface defines `void notifyUser()`.
2. **Channel Implementations**:
   - `SMSNotification`: Prints `"Sending an SMS notification."`
   - `EmailNotification`: Prints `"Sending an Email notification."`
   - `PushNotification`: Prints `"Sending a Push notification."`
   - `SlackMessageNotification`: Demonstrates adding a 4th channel with zero disruption.
3. **Factory Method (`createNotification`)**: Maps requested channel strings to corresponding instances, throwing `IllegalArgumentException` on unrecognized channels.
4. **Decoupled Client**: The caller simply invokes:
   ```java
   Notification alert = NotificationFactory.createNotification("SMS");
   alert.notifyUser();
   ```

---

## 4. How to Run & Sample Output

### Compilation & Execution
```bash
javac B2_NotificationFactory.java
java B2_NotificationFactory
```

### Sample Output
```
Sending an SMS notification.
Sending an Email notification.
Sending a Push notification.
Sending a Slack message notification.
```
