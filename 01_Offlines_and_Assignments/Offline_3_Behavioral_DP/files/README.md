# CSE-214 Offline 3 — Behavioural Design Patterns

## Task 1: BD Alert — Observer Design Pattern (15 marks)

**Pattern identified:** Observer.

**Why:** Each disaster category (`EARTHQUAKE`, `FLOOD`, `FIRE`) is a one-to-many
relationship between "state changes" (new alerts) and interested parties
(citizens). Citizens subscribe/unsubscribe dynamically, and a publisher must
push updates automatically to exactly its current subscribers without knowing
their concrete types in advance — the textbook Subject/Observer relationship.

**Key classes:**
- `AlertObserver` (Observer interface) / `Citizen` (Concrete Observer)
- `Subject` (Subject interface) / `AlertCategorySubject` (Concrete Subject, one per category)
- `Alert` — plain data object (title, category, location, severity, instructions)
- `BDAlertSystem` — facade that owns one `AlertCategorySubject` per category and
  exposes registration/subscription/publishing operations
- `Main` — demonstration driver

**Run:**
```
cd Task1_Observer_DisasterAlert
javac *.java
java Main
```

**What the demo proves:**
- Multiple citizens with different, overlapping subscriptions
- One earthquake, one flood, one fire alert published
- A citizen who subscribes to FLOOD *after* a flood alert was published does
  **not** receive that earlier alert (future-alerts-only requirement)
- Subscription updates (unsubscribe + resubscribe) verified by publishing a
  second round of alerts
- Each citizen's full notification history displayed at the end

---

## Task 2: BUET Final Result Publication — Mediator Design Pattern (10 marks)

**Pattern identified:** Mediator.

**Why:** The Department Office, Controller of Examinations, DSW, and the
Student must never call each other directly — all communication is required
to pass through a single coordinator that also enforces a strict sequence.
This centralizes control logic in one object instead of spreading
interdependencies across four colleague classes — the defining trait of
Mediator.

**Key classes:**
- `ResultCoordinator` (Mediator interface) / `ResultProcessingCoordinator` (Concrete Mediator)
- `Colleague` (abstract base) → `DepartmentOffice`, `ControllerOfExaminations`, `DSW`, `Student`
- `ProcessingStatus` — per-student state tracked privately inside the mediator
- `Main` — demonstration driver

**Run:**
```
cd Task2_Mediator_ResultPublication
javac *.java
java Main
```

**What the demo proves (in the exact required order):**
1. Office order attempted before departmental confirmation → **rejected**
2. Departmental confirmation submitted → recorded, student notified
3. Certificate/transcript attempted early → **rejected** (testimonial missing)
4. Office order issued (now allowed) → student notified
5. Certificate/transcript attempted again → **rejected** (testimonial still missing)
6. Testimonial issued (DSW) → student notified
7. Certificate & transcript issued (now allowed) → student notified
8. Final status displayed, showing all four steps as DONE

Both programs were compiled and run successfully with OpenJDK 21.
