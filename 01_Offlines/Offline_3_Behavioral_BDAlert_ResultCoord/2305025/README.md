# Offline 3: Official Submission Package (Student ID: 2305025)

**Course:** BUET CSE 214: Software Engineering Sessional
**Assignment:** Offline 3 (Behavioral Design Patterns)
**Author:** Shadman S. Shuvo (`2305025`)

---

## 📦 Directory Structure

```
2305025/
├── alert/                     # Task 1: BD Alert System (Observer Pattern)
│   ├── Alert.java             # Emergency alert record (category, severity, location, message)
│   ├── AlertCategory.java     # Enum: EARTHQUAKE, FLOOD, FIRE, CYCLONE
│   ├── AlertObserver.java     # Observer interface declaring update(Alert alert)
│   ├── AlertSubject.java      # Concrete subject managing subscriber collection per category
│   ├── AlertSystem.java       # Facade orchestrating multi-category dispatch
│   ├── Citizen.java           # Concrete observer maintaining notification logs
│   ├── Subject.java           # Subject interface (attach, detach, notifyObservers)
│   └── Main.java              # Demonstration driver for Task 1
│
└── result/                    # Task 2: BUET Final Result Coordinator (Mediator Pattern)
    ├── Colleague.java         # Abstract base class for collaborating entities
    ├── Coordinator.java       # Mediator interface declaring office order, testimonial & issuance
    ├── CtrlOffice.java        # Controller of Examinations colleague
    ├── DeptOffice.java        # Academic Department Office colleague
    ├── DSW.java               # Directorate of Students' Welfare colleague
    ├── Student.java           # Graduating Student colleague
    ├── ResultCoord.java       # Concrete mediator coordinating the 4 colleagues
    ├── Status.java            # Processing status enum (PENDING, DEPT_CLEARED, ORDERED, etc.)
    └── Main.java              # Demonstration driver for Task 2
```

---

## ⚡ Compilation & Execution

### Running Task 1 (BD Alert)
```bash
cd alert
javac *.java
java Main
```
**Expected Output Highlights:**
- Verifies registration of citizens Rahim, Karim, Fatema, and Nusrat.
- Demonstrates earthquake, flood, and fire alerts dispatched only to subscribed citizens.
- Proves Nusrat (who subscribes to Flood *after* an alert is published) does not receive stale alerts.
- Displays full alert history for all citizens.

---

### Running Task 2 (Result Coordinator)
```bash
cd result
javac *.java
java Main
```
**Expected Output Highlights:**
- Early attempts to issue office orders or certificates without departmental clearance are safely rejected.
- Step-by-step clearance sequence: Department Verification $\to$ Controller Office Order $\to$ DSW Testimonial $\to$ Certificate & Transcript Issuance.
- Student checks status and confirms graduation clearance.
