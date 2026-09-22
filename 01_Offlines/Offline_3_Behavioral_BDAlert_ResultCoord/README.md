# Offline 3: Disaster Alert & BUET Result Coordinator (Behavioral Patterns)

**Course:** BUET CSE 214: Software Engineering Sessional  
**Assignment:** Offline 3 (Behavioral Design Patterns)  
**Student ID:** `2305025`  
**Core Patterns:** **Observer Pattern** (Task 1), **Mediator Pattern** (Task 2)  

---

## 📋 Assignment Overview

Offline 3 addresses two distinct real-world distributed collaboration problems using Gang of Four behavioral patterns:
1. **Task 1: BD Alert System** — Real-time disaster broadcast notification using the **Observer Pattern**.
2. **Task 2: BUET Final Result Publication** — Multi-departmental graduation approval workflow using the **Mediator Pattern**.

---

## 🚨 Task 1: BD Alert System (Observer Pattern)

### Problem Description
Design an emergency disaster alert broadcasting network for Bangladesh. When meteorological or disaster monitoring authorities publish an alert (e.g. Earthquake, Flood, Cyclone, Fire), registered citizens must receive real-time alerts.

### Architectural Solution
- **Subject Interface (`Subject`) & Concrete Subject (`AlertSubject`):** Maintains subscriber lists partitioned by hazard category (`AlertCategory`: `EARTHQUAKE`, `FLOOD`, `FIRE`, `CYCLONE`).
- **Observer Interface (`AlertObserver`) & Concrete Observer (`Citizen`):** Citizens subscribe dynamically to one or more hazard categories.
- **Key Behavioral Requirements Satisfied:**
  - **No Retroactive Alerts:** A citizen who subscribes to `FLOOD` *after* a flood alert has already been broadcast does not receive the past alert.
  - **Dynamic Subscription Mutation:** Citizens can subscribe or unsubscribe at any time without restarting the system.
  - **Notification History:** Each citizen maintains an internal chronological log of received emergency bulletins.

```
                    +------------------------+
                    |        Subject         |
                    +------------------------+
                    | +attach(observer)      |
                    | +detach(observer)      |
                    | +notifyObservers(...)  |
                    +------------------------+
                                ^
                                |
                    +------------------------+          notifies          +-----------------------+
                    |      AlertSubject      | -------------------------> |     AlertObserver     |
                    +------------------------+                            +-----------------------+
                    | - category: Category   |                            | +update(alert): void  |
                    | - observers: List      |                            +-----------------------+
                    +------------------------+                                        ^
                                                                                      |
                                                                          +-----------------------+
                                                                          |        Citizen        |
                                                                          +-----------------------+
                                                                          | - name: String        |
                                                                          | - history: List<Alert>|
                                                                          +-----------------------+
```

---

## 🎓 Task 2: BUET Result Publication (Mediator Pattern)

### Problem Description
At BUET, graduating students undergo a strict multi-departmental clearance and result publication process involving four independent entities:
1. **Department Office:** Verifies completion of all degree requirements and thesis credits.
2. **Controller of Examinations:** Formulates official office orders and issues transcripts & degree certificates.
3. **Directorate of Students' Welfare (DSW):** Checks non-academic clearances (hall dues, discipline) and issues the testimonial.
4. **Student:** Inquires about processing status and receives graduation documents.

If these offices interacted directly, an $O(N^2)$ tangled dependency web would emerge.

### Architectural Solution: Mediator Pattern
- **Mediator Interface (`Coordinator`) & Concrete Mediator (`ResultCoord` / `ResultProcessingCoordinator`):**
  - Centralizes all communication rules, state transitions, and prerequisite validations.
  - The four colleague entities never communicate with each other directly; every interaction is routed through the mediator.
- **Strict State Machine Ordering:**
  1. Department Office submits `confirmCompletion(studentId)`.
  2. Controller Office issues `issueOfficeOrder(studentId)` (blocked if step 1 is incomplete).
  3. DSW issues `issueTestimonial(studentId)` (blocked if step 2 is incomplete).
  4. Controller Office issues `issueCertificateAndTranscript(studentId)` (blocked if step 3 is incomplete).
  5. Student checks status and receives notifications at each transition.

---

## 📁 Directory Structure

| Folder | Description |
| :--- | :--- |
| **[`2305025/`](2305025/)** | **Official evaluated submission package** containing clean `alert/` and `result/` packages. |
| **[`2305025v1/`](2305025v1/)** & **[`2305025v2/`](2305025v2/)** | Previous development iterations of student submission. |
| **[`claude/`](claude/)** | Reference architectural implementation and documentation. |
| **[`gpt/`](gpt/)** | Alternative exploration implementation. |
| **[`files-v2/`](files-v2/)** | Initial problem templates and auxiliary source files. |
| **[`CSE-214_Offline-3_Formatted.pdf`](CSE-214_Offline-3_Formatted.pdf)** | Formal assignment specification document. |

---

## 🚀 How to Compile & Run

### Task 1: BD Alert (Observer)
```bash
cd 2305025/alert
javac *.java
java Main
```

### Task 2: Result Publication (Mediator)
```bash
cd 2305025/result
javac *.java
java Main
```
