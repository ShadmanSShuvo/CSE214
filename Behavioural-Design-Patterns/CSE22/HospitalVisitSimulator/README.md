# Problem Statement: Hospital Visit Simulator

**Batch**: CSE22
**Source**: `Behavioral_Online_Questions.pdf` (Subsection: C1)
**Time**: 25 minutes
**Design Pattern**: **Template Method Pattern**

---

## 1. Problem Description

You are building a small hospital visit simulator. Every patient visit must follow the same overall flow, and the system should print each step in order:
1. **Check-In**: Register patient name + assign visit ID.
2. **Record Vitals**: Print temperature + blood pressure.
3. **Assessment**: Customized per department.
4. **Treatment**: Customized per department.
5. **Discharge Summary**: Print “patient discharged” + notes.

### Department Customizations:
- **General Department**:
  - *Assessment*: “Doctor performs normal diagnosis”
  - *Treatment*: “Prescribe standard medicine”
- **Pediatrics Department**:
  - *Assessment*: “Doctor checks symptoms by ensuring child comfort level”
  - *Treatment*: “Give child-safe medicine, friendly reassurance message”
- **Emergency Department**:
  - *Assessment*: “Quick triage check (urgent/non-urgent)”
  - *Treatment*: “Immediate emergency procedure”

---

## 2. Design Pattern Justification: Template Method Pattern

- **Why Template Method?**:
  All departments follow the exact same skeleton flow of 5 steps in the same invariable order.
  The base abstract class (`HospitalVisitTemplate`) defines the `final` template method `processVisit(Patient patient)`.
  Common steps (`checkIn`, `recordVitals`, `dischargeSummary`) are implemented once in the base class, while varying steps (`assessment`, `treatment`) are defined as abstract methods overridden by concrete department subclasses (`GeneralDepartmentVisit`, `PediatricsDepartmentVisit`, `EmergencyDepartmentVisit`).

---

## 3. Class Diagram

```
+-------------------------------------------------------+
|             HospitalVisitTemplate                     |
+-------------------------------------------------------+
| + processVisit(patient: Patient) : void <<final>>     |
| # checkIn(patient: Patient) : void                    |
| # recordVitals(patient: Patient) : void               |
| # assessment(patient: Patient) : void <<abstract>>    |
| # treatment(patient: Patient) : void <<abstract>>     |
| # dischargeSummary(patient: Patient) : void           |
+-------------------------------------------------------+
       ^                         ^                         ^
       |                         |                         |
+----------------------+ +-------------------------+ +------------------------+
| GeneralDepartment... | | PediatricsDepartment... | | EmergencyDepartment... |
+----------------------+ +-------------------------+ +------------------------+
| # assessment(...)    | | # assessment(...)       | | # assessment(...)      |
| # treatment(...)     | | # treatment(...)        | | # treatment(...)       |
+----------------------+ +-------------------------+ +------------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE22/HospitalVisitSimulator
javac *.java
java Main
```
