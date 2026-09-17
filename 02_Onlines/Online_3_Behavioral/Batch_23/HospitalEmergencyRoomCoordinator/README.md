# Problem Statement: Hospital Emergency Room Coordinator

**Batch**: CSE23
**Source**: `Hospital Emergency Room Coordinator.pdf`
**Duration**: 30 Minutes
**Design Pattern**: **Mediator Pattern**

---

## 1. Problem Description

A hospital emergency department handles patients through several medical units:
- **Doctor**
- **Pathology Lab**
- **Radiology Unit**
- **Patient**

A patient is initially examined by the Doctor, who may request a pathology test, a radiology investigation, or both.

### Coordination Rule:
The medical units **must not communicate directly with each other**. All requests, results, and notifications pass through a central **Emergency Center**, which coordinates the workflow and keeps track of pending investigations.

### Result Coordination:
- **Case 1 (Single Investigation)**:
  Forwards result to both Doctor and Patient as soon as available &rarr; complete.
- **Case 2 (Both Investigations)**:
  Waits until both results are available before sending complete investigation summary.
- **Urgent Result Handling**:
  Occurs when Pathology reports **CRITICAL** or Radiology reports **NOT OK**.
  The Emergency Center immediately notifies both Doctor and Patient, even if another investigation is pending, while keeping the investigation open until all investigations conclude.

### Expected Output from Problem Statement:
```
Pathology test requested for Patient P101.
Radiology investigation requested for Patient P101.
Critical pathology result received for Patient P101.
URGENT notification sent to Doctor.
URGENT notification sent to Patient P101.
Radiology result received for Patient P101.
All requested investigations completed for Patient P101.
Complete results sent to Doctor.
Complete results sent to Patient P101.
```

---

## 2. Design Pattern Justification: Mediator Pattern

- **Why Mediator Pattern?**:
  Direct communication between Doctor, Pathology Lab, Radiology, and Patient creates a tangled $N \times N$ web of dependencies.
- With `EmergencyCenter` serving as the central **Mediator**, all medical units depend solely on the mediator abstraction. The mediator encapsulates:
  1. Routing requests to specialized units.
  2. Patient-by-patient tracking of pending vs. completed investigations.
  3. Real-time urgency triage and conditional summary dispatching.
  4. Extensibility: New diagnostic units (e.g., Cardiology / ECG) can be plugged in without changing existing units.

---

## 3. Architecture Diagram

```
                 +------------------------------------+
                 |             DoctorUnit             |
                 +------------------------------------+
                                   |
                     requests / complete / alerts
                                   v
                 +------------------------------------+
                 |          EmergencyCenter           |
                 |             (Mediator)             |
                 +------------------------------------+
                   /            |                 \
                  /             |                  \
                 v              v                   v
+------------------+  +--------------------+  +---------------+
|   PathologyLab   |  |   RadiologyUnit    |  |    Patient    |
+------------------+  +--------------------+  +---------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE23/HospitalEmergencyRoomCoordinator
javac *.java
java Main
```
