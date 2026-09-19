# Batch 22 Online 2 (B1) - Dlachal Subscription Notifications (Bridge Pattern)

## Problem Statement
Dlachal is an online grocery platform that needs to keep users informed about key subscription events. Whenever a user's monthly bazar is confirmed, dispatched, or renewed, or when payment fails, the system must send out a notification.

The system faces two independent dimensions of change:
1. **Diverse and Expanding Channels**: Users receive alerts via Email, SMS, WhatsApp, and in the future, Push Notifications.
2. **Evolving Event Notifications**: Different events require different message wording, tone, and logic (e.g., `BazarConfirmed`, `BazarDispatched`, `PaymentFailed`, etc.).

**Goal**: Design a clean, maintainable notification system that allows introducing new channels and new event types independently without exploding into an unmanageable matrix of classes ($M \times N$).

---

## Design Pattern Analysis

### Pattern Applied: **Bridge Pattern**

### Why Bridge?
- **Two Orthogonal Dimensions**: Event logic (Abstraction) varies independently from delivery technology (Implementor).
- **Avoiding Combinatorial Subclass Explosion**: Without Bridge, supporting $M$ event types across $N$ notification channels requires $M \times N$ classes (`EmailBazarConfirmed`, `SMSBazarConfirmed`, `WhatsAppBazarConfirmed`, etc.). With Bridge, only $M + N$ classes are required.
- **Runtime Flexibility**: A notification can dynamically switch transport channels or be configured at runtime without changing event-specific formatting.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Abstraction** | `Notification` | Base class defining notification event logic; holds `MessageSender` reference. |
| **Refined Abstractions** | `BazarConfirmedNotification`, `BazarDispatchedNotification`, `PaymentFailedNotification` | Specific business events formatting alert text. |
| **Implementor** | `MessageSender` | Interface defining `sendMessage(String content)`. |
| **Concrete Implementors** | `EmailSender`, `SMSSender`, `WhatsAppSender` | Concrete channel dispatchers. |
| **Client** | `B1Bridge` | Pairs events with channels and dispatches alerts. |

---

## Class Architecture

```
         Notification (Abstraction)   ----[bridge]---->   MessageSender (Implementor)
         -sender: MessageSender                          +sendMessage(content: String)
         +notifyUser(userInfo: String)                                 ^
                     ^                                                 |
         +-----------+-----------+                      +--------------+--------------+
         |                       |                      |              |              |
BazarConfirmedNotification  PaymentFailedNotification  EmailSender  SMSSender   WhatsAppSender
```

---

## Solution Walkthrough

1. **Implementor Hierarchy**:
   ```java
   interface MessageSender {
       void sendMessage(String content);
   }
   class EmailSender implements MessageSender {
       public void sendMessage(String content) { System.out.println("Email -> " + content); }
   }
   class SMSSender implements MessageSender {
       public void sendMessage(String content) { System.out.println("SMS -> " + content); }
   }
   class WhatsAppSender implements MessageSender {
       public void sendMessage(String content) { System.out.println("WhatsApp -> " + content); }
   }
   ```
2. **Abstraction Hierarchy**:
   ```java
   abstract class Notification {
       protected MessageSender sender;
       Notification(MessageSender sender) { this.sender = sender; }
       abstract void notifyUser(String userInfo);
   }
   class BazarDispatchedNotification extends Notification {
       BazarDispatchedNotification(MessageSender sender) { super(sender); }
       void notifyUser(String userInfo) {
           sender.sendMessage("Hi " + userInfo + ", your bazar is on the way!");
       }
   }
   class PaymentFailedNotification extends Notification {
       PaymentFailedNotification(MessageSender sender) { super(sender); }
       void notifyUser(String userInfo) {
           sender.sendMessage("Hi " + userInfo + ", your payment failed. Please retry.");
       }
   }
   ```
3. **Decoupled Execution**:
   ```java
   Notification n1 = new BazarDispatchedNotification(new WhatsAppSender());
   n1.notifyUser("Rafi");

   Notification n2 = new PaymentFailedNotification(new SMSSender());
   n2.notifyUser("Nadia");
   ```

---

## How to Compile & Run

```bash
cd Batch_22/B1_DlachalNotificationBridge
javac *.java
java B1Bridge
```

### Output
```
WhatsApp -> Hi Rafi, your bazar is on the way!
SMS -> Hi Nadia, your payment failed. Please retry.
```
