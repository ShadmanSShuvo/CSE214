# Offline 2: SmartHome Automation Hub (Structural Patterns)

**Course:** BUET CSE 214: Software Engineering Sessional  
**Assignment:** Offline 2 (Structural Design Patterns)  
**Student ID:** `2305025`  
**Core Patterns:** **Composite Pattern**, **Decorator Pattern**  

---

## 🏡 Problem Overview

NexaHome requires an enterprise smart home automation system to monitor and control diverse IoT hardware across individual devices, individual rooms, and entire multi-room homes uniformly.

### Core Domain Rules:
1. **Device Types:**
   - `SmartLight`: Consumes 10W when active.
   - `SmartThermostat`: Consumes 150W when active.
   - `SmartSpeaker`: Consumes 5W when active.
2. **Uniform Interface:** Every entity (single device, room, or home) must support:
   - `void activate()`
   - `void deactivate()`
   - `String getStatus()`
   - `double getPowerUsage()` (returns 0 when inactive)

---

## 🏛️ Structural Architecture

```
                          <<interface>>
                           SmartDevice
        +-----------------------------------------------+
        | +activate(): void                             |
        | +deactivate(): void                           |
        | +getPowerUsage(): double                      |
        | +getStatus(): String                          |
        +-----------------------------------------------+
           ^                     ^                    ^
           | implements          | implements         | implements
    +--------------+      +--------------+      +-------------------+
    | Leaf Devices |      |  Composites  |      |    Decorators     |
    +--------------+      +--------------+      +-------------------+
    | - SmartLight |      | - Room       |      | Device-Level:     |
    | - Thermostat |      | - Home       |      | - AccessRestricted|
    | - Speaker    |      +--------------+      | - TimerControlled |
    +--------------+                            | - PowerThrottled  |
                                                | Room/Home-Level:  |
                                                | - EcoMode         |
                                                | - GuestMode       |
                                                +-------------------+
```

### 1. The Composite Pattern
- **Component Interface:** `SmartDevice`
- **Leaves:** `SmartLight`, `SmartThermostat`, `SmartSpeaker`
- **Composites:** `Room` (contains a list of `SmartDevice`), `Home` (contains rooms and standalone devices).
- **Benefit:** Clients invoke `.activate()`, `.deactivate()`, or `.getPowerUsage()` on a `Home` object, which recursively propagates through all child rooms and leaf devices without conditional type checking.

### 2. The Decorator Pattern
- **Device-Level Enhancements (Stackable):**
  - **`AccessRestricted`**: Blocks control actions until unlocked with a 4-digit PIN. Power draw persists if locked while running.
  - **`TimerControlled`**: Automatically triggers deactivation upon timer expiration.
  - **`PowerThrottled`**: Imposes a strict wattage cap (e.g. throttling a 150W thermostat to 80W).
- **Container-Level Enhancements (Room / Home):**
  - **`EcoMode`**: Enforces an aggregate power budget (e.g., 100W). If total active power exceeds the budget, devices are deactivated in reverse order of installation (most recently added devices are shed first).
  - **`GuestMode`**: Restricts operation to allowed device types (e.g. lights and speakers only). Prohibited devices remain inactive and report zero guest power.

---

## 🔬 Stacking & Order Sensitivity (Demo D)

Because decorators wrap components, decorator ordering produces distinct behaviors:
- **Throttled-then-Eco:** Throttling a thermostat to 80W before adding it to an EcoMode room (100W budget) allows both the thermostat (80W) and two 10W lights ($80 + 10 + 10 = 100\text{W}$) to run concurrently.
- **Raw-then-Eco:** Adding an unthrottled thermostat (150W) directly forces EcoMode to shed the thermostat immediately because it alone exceeds the 100W ceiling.

---

## 📁 Directory Structure

| Folder / File | Description |
| :--- | :--- |
| **[`2305025/`](2305025/)** | **Official evaluated submission** (`SmartHome.java` and `SmartHomeTestRunner.java`). |
| **[`fixed-after-IJ.java`](fixed-after-IJ.java)** | Alternative implementation variant utilizing container device accessor methods. |
| **[`SmartHome/`](SmartHome/)** | **Full-Stack Application** with Java backend and an interactive Vite + React + TypeScript web GUI. |
| **[`Code/`](Code/)** | Baseline unrefactored spaghetti demonstration (`SmartHomeSpaghettiDemo.java`). |
| **[`Spec/`](Spec/)** | Formal assignment specification documents (`.pdf` and `.docx`). |

---

## 🚀 How to Compile & Run

### Running the Official Submission
```bash
cd 2305025
javac *.java
java SmartHome
```

### Running Test Verification Runner
```bash
cd 2305025
javac *.java
java SmartHomeTestRunner
```

### Running the Full-Stack Web GUI
```bash
cd SmartHome/frontend
npm install
npm run dev
```
Navigate to `http://localhost:5173`.
