# SmartHome Automation Hub: Official Submission (2305025)

**Course:** BUET CSE 214: Software Engineering Sessional
**Assignment:** Offline 2 (Structural Design Patterns)
**Author:** Shadman S. Shuvo (`2305025`)

---

## 🛠️ Implementation Summary

The submission file `SmartHome.java` provides a self-contained, robust architecture implementing the Composite and Decorator patterns according to NexaHome specifications.

### 1. Concrete Classes & Roles
| Class | Category | Role & Capabilities |
| :--- | :--- | :--- |
| `SmartDevice` | **Component Interface** | Base contract declaring `activate()`, `deactivate()`, `getPowerUsage()`, and `getStatus()`. |
| `SmartLight` | **Leaf** | Consumes 10W when on. |
| `SmartThermostat` | **Leaf** | Consumes 150W when on. |
| `SmartSpeaker` | **Leaf** | Consumes 5W when on. |
| `Room` | **Composite** | Maintains an ordered list of `SmartDevice` entities; delegates lifecycle and aggregates wattage. |
| `Home` | **Composite** | Top-level container aggregating multiple `Room` instances and standalone devices. |
| `AccessRestricted` | **Device Decorator** | PIN protection; blocks commands unless unlocked with the matching PIN. |
| `TimerControlled` | **Device Decorator** | Automatic shutoff upon simulation of timer expiry. |
| `PowerThrottled` | **Device Decorator** | Restricts wattage to a user-defined threshold. |
| `EcoMode` | **Container Decorator** | Sheds devices in reverse installation order if aggregate power exceeds budget. |
| `GuestMode` | **Container Decorator** | Filters execution and power reporting to allowed device types only. |

---

## 🔬 Demonstration Suite Walkthrough

The `main` driver runs six comprehensive scenarios proving structural correctness:
- **DEMO A (Home Overview):** Basic activation and power consumption of lights and thermostats across rooms.
- **DEMO B (Chained Device Decorators):** `AccessRestricted(TimerControlled(SmartLight))`. Tests incorrect PIN rejection, successful unlock, and subsequent timer expiration.
- **DEMO C (EcoMode Power Shedding):** Room with $10\text{W} + 10\text{W} + 150\text{W} = 170\text{W}$ under a 100W budget automatically sheds the thermostat, keeping the two lights active (20W total).
- **DEMO D (Decorator Stacking Order Sensitivity):** Demonstrates the quantitative difference between throttling a thermostat to 80W before adding it to an EcoMode room vs. adding an unthrottled 150W thermostat.
- **DEMO E (Guest Mode Filtering):** Whitelist filtering ensuring only authorized device classes respond to activation.
- **DEMO F (Night Preparation on Entire Room):** Wrapping a multi-device `Room` in `AccessRestricted` and `TimerControlled`, showing that composites can be decorated identically to individual leaves.

---

## 🚀 Compilation & Test Execution

### 1. Compile All Files
```bash
javac *.java
```

### 2. Run Main Demonstration
```bash
java SmartHome
```

### 3. Run Automated Assertion Test Suite
```bash
java SmartHomeTestRunner
```
The test runner executes automated assertion checks verifying all power totals, shedding sequences, and access denials.
