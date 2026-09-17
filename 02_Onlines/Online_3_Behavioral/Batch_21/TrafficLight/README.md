# Problem Statement: Traffic Light System

**Batch**: CSE21
**Source**: `Sec_B.pdf`
**Duration**: 25 Minutes
**Design Pattern**: **State Pattern**

---

## 1. Problem Description

We need to create a traffic light system that follows a simple cycle with three colors: red, yellow, and green. Here's how it should work:
- When the system starts, the light should be **red**.
- The **red light** should stay on for **5 seconds**.
- After 5 seconds, the light should switch to **yellow** for **2 seconds**.
- After 2 seconds, the light should switch to **green** for **10 seconds**.
- After 10 seconds, the light should go back to **red**, and the cycle starts over.

Timing control specification:
```java
try {
    Thread.sleep(1000); // Wait for 1 second
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

---

## 2. Design Pattern Justification: State Pattern

- **Why State Pattern?**:
  Each color (Red, Yellow, Green) represents an autonomous state with its own specific behavior:
  - Knowing its display color and countdown duration.
  - Dictating which state comes next in the sequence.
- Encapsulating each light color as a concrete `TrafficLightState` class decouples the state transitions from the controller context (`TrafficLightContext`), cleanly eliminating monolithic switch/if-else logic and making timing or color sequence alterations straightforward.

---

## 3. State Cycle Diagram

```
         +---------------------------------------+
         |                                       |
         v                                       |
+-------------------+                           |
|     Red State     | (5 seconds)               |
+-------------------+                           |
         |                                       |
         v transitions to                        |
+-------------------+                           |
|   Yellow State    | (2 seconds)               |
+-------------------+                           |
         |                                       |
         v transitions to                        |
+-------------------+                           |
|    Green State    | (10 seconds)              |
+-------------------+                           |
         |                                       |
         +---------------------------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE21/TrafficLight
javac *.java
java Main
```
