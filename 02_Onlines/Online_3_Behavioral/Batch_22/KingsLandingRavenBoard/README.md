# Problem Statement: King's Landing RavenBoard

**Batch**: CSE22
**Source**: `Behavioral_Online_Questions.pdf` (Subsection: A2)
**Time**: 20 minutes
**Design Pattern**: **Observer Pattern**

---

## 1. Problem Description

King’s Landing has a giant message board where ravens deliver scrolls such as:
- “Enemy spotted near the river”
- “Winter supplies running low”
- “Ships seen in the east”

Your task is to build a **RavenBoard** system with an appropriate design pattern where multiple groups can receive new messages: **Commander**, **Scouts**, **Supply Team**, etc., and act based on it:
- Scouts: *“Dispatch riders!”*
- Supply Team: *“Update inventory!”*
- Commander: *“Man the walls and prepare defenses!”*

Groups must be able to **subscribe and unsubscribe at runtime** (e.g., Scouts leaving the board room).

Demonstrate your implementation using 3 messages, subscribe/unsubscribe actions, and group response prints.

---

## 2. Design Pattern Justification: Observer Pattern

- **Why Observer?**:
  The `RavenBoard` acts as the Subject/Publisher. Multiple military and logistical factions act as Observers.
  When ravens deliver a scroll, the board broadcasts it to all currently subscribed groups. Groups can enter (subscribe) or leave (unsubscribe) the war room dynamically without modifying the board or other groups.

---

## 3. Class Diagram

```
                 +----------------------------------------+
                 |               RavenBoard               |
                 +----------------------------------------+
                 | - observers: List<KingdomObserver>     |
                 | + subscribe(observer: KingdomObserver) |
                 | + unsubscribe(observer)                |
                 | + deliverScroll(scrollMessage: String) |
                 +----------------------------------------+
                                     |
                                     v notifies
                 +----------------------------------------+
                 |            <<interface>>               |
                 |           KingdomObserver              |
                 +----------------------------------------+
                 | + getName(): String                    |
                 | + onScrollDelivered(scroll: String)    |
                 +----------------------------------------+
                     ^              ^               ^
                     |              |               |
         +-----------+              |               +-----------+
         |                          |                           |
+-------------------+      +-------------------+       +-------------------+
|     Commander     |      |      Scouts       |       |    SupplyTeam     |
+-------------------+      +-------------------+       +-------------------+
| + onScroll(...)   |      | + onScroll(...)   |       | + onScroll(...)   |
+-------------------+      +-------------------+       +-------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE22/KingsLandingRavenBoard
javac *.java
java Main
```
