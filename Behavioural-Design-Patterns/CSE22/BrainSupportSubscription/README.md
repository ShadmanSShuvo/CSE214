# Problem Statement: Brain-Support Subscription Simulator

**Batch**: CSE22
**Source**: `Behavioral_Online_Questions.pdf` (Subsection: A1)
**Time**: 30 minutes
**Design Pattern**: **State Pattern**

---

## 1. Problem Description

A tech company offers a monthly brain-support subscription for patients with severe brain injury, and the service must remain active for the patient to function normally.

The subscription has three tiers: **Common**, **Plus**, and **Lux**.
Each tier provides a coverage radius from regional server towers that determines whether the patient stays responsive during travel:
- **Common**: safe range is **0–10 km**
- **Plus**: safe range is **0–50 km**
- **Lux**: safe range is **0–50 km** (plus Mood Control enabled)

### Required Operations:
1. `travelCheck(km)`:
   - Prints whether the patient is **STABLE** or **UNSTABLE** at that distance under the current tier.
   - If distance exceeds the tier's safe range, the patient becomes **UNSTABLE** (the patient blacks out). The log must print an alert message asking to bring the patient back into coverage.
   - When brought back into coverage (`travelCheck(0km)`), the patient regains consciousness and becomes **STABLE**.
2. `activateLux(hours)`:
   - Temporarily switches the service into **Lux** for the specified duration (using `Thread.sleep()` or simulated time).
   - Then automatically returns to whichever tier (**Common** or **Plus**) was active right before Lux was activated.
3. `setMood(calm | exhausted | happy)`:
   - Only works while **Lux** is active.
   - Otherwise, prints: `"Mood control unavailable."`
4. `promote()`:
   - Takes Common &rarr; Plus, Plus &rarr; Lux, Lux &rarr; Lux (no change).
5. `demote()`:
   - Takes Lux &rarr; Plus, Plus &rarr; Common, Common &rarr; Common (no change).

---

## 2. Design Pattern Justification: State Pattern

- **Why State Pattern?**:
  The system behavior (safe coverage radius, mood control availability, promotion/demotion transitions) depends directly on the current subscription tier state.
- Encapsulating each subscription tier as an independent state implementing `SubscriptionTier` avoids rigid switch/if-else logic and allows seamless dynamic transitions, temporary tier switches (with restoration of previous state), and tier-specific privilege enforcement.

---

## 3. Tier Transition Diagram

```
       promote()                     promote()
[Common] --------> [Plus] ------------------------> [Lux]
(0-10 km)          (0-50 km)                        (0-50 km + Mood Control)
   ^                  |                                |
   |                  v demote()                       |
   +------------------+ <------------------------------+
                                   demote()
```

---

## 4. How to Compile & Run

```bash
cd CSE22/BrainSupportSubscription
javac *.java
java Main
```
