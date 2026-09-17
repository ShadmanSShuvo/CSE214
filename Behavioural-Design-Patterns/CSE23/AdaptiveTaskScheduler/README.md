# Problem Statement: Adaptive Task Scheduler

**Batch**: CSE23
**Source**: `Adaptive Task Scheduler.pdf`
**Duration**: 30 Minutes
**Design Pattern**: **Strategy Pattern** (Dynamic Adaptive Strategy Selection)

---

## 1. Problem Description

A computing system maintains a queue of tasks waiting to be executed. Each task contains:
- Task ID
- Start Time
- End Time
- Priority: `HIGH`, `MEDIUM`, or `LOW`

Execution Time is calculated as:
$$\text{Execution Time} = \text{End Time} - \text{Start Time}$$

### The Three Scheduling Policies:
1. **FCFS (First Come First Served)**: Tasks are selected according to their start time (earliest start time first).
2. **Priority Scheduling**: Tasks are selected by priority (`HIGH` > `MEDIUM` > `LOW`). Tie-breaker: earliest start time.
3. **SJF (Shortest Job First)**: Tasks are selected by smallest execution time. Tie-breaker: earliest start time.

### Adaptive Scheduling Rules (evaluated before selecting *every* next task):
- **Rule 1 (Urgent Workload)**: If at least one waiting task has `HIGH` priority, the scheduler **must use Priority Scheduling**, regardless of the user's preferred policy.
- **Rule 2 (Short-Task Workload)**: If there is no `HIGH` priority task waiting, but **at least three waiting tasks have execution time $\le 3$ units**, the scheduler **must use SJF**.
- **Rule 3 (Normal Workload)**: If neither condition is satisfied, the scheduler uses the **preferred scheduling policy** selected by the user.

The scheduling decision must be made again after each task is executed.

### Required Operations:
- `addTask(...)`
- `setPreferredPolicy(...)`
- `executeNextTask()`
- `executeAll()`

---

## 2. Design Pattern Justification: Strategy Pattern

- **Why Strategy Pattern?**:
  Each scheduling algorithm (`FCFS`, `Priority`, `SJF`) encapsulates a task-selection algorithm behind a common `SchedulingPolicy` interface.
- **Adaptive Context**: The `TaskScheduler` context evaluates the workload state before every selection and dynamically switches the active strategy between FCFS, Priority, and SJF without mutating the scheduler itself or replacing the queue.

---

## 3. Class Diagram

```
                 +-----------------------------------------------+
                 |                 TaskScheduler                 |
                 +-----------------------------------------------+
                 | - waitingQueue: List<Task>                    |
                 | - preferredPolicy: SchedulingPolicy           |
                 | + addTask(task: Task)                         |
                 | + setPreferredPolicy(policy)                  |
                 | + determineEffectivePolicy(): SchedulingPolicy|
                 | + executeNextTask(): Task                     |
                 | + executeAll(): void                          |
                 +-----------------------------------------------+
                                         |
                                         v uses
                 +-----------------------------------------------+
                 |                 <<interface>>                 |
                 |               SchedulingPolicy                |
                 +-----------------------------------------------+
                 | + selectNextTask(queue: List<Task>): Task     |
                 | + getPolicyName(): String                     |
                 +-----------------------------------------------+
                     ^                   ^                  ^
                     |                   |                  |
         +-----------+                   |                  +-----------+
         |                               |                              |
+------------------+           +--------------------+         +--------------------+
|    FCFSPolicy    |           | PriorityScheduling |         |     SJFPolicy      |
+------------------+           +--------------------+         +--------------------+
| + selectNextTask |           | + selectNextTask   |         | + selectNextTask   |
+------------------+           +--------------------+         +--------------------+
```

---

## 4. How to Compile & Run

```bash
cd CSE23/AdaptiveTaskScheduler
javac *.java
java Main
```
