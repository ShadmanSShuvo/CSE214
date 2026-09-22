# SmartHome: Java Backend

This directory contains the core Java backend domain models and test runner for the **SmartHome Automation Hub** full-stack application.

- **`SmartHome.java`**: Implements the Composite (`Room`, `Home`, `SmartDevice`) and Decorator (`AccessRestricted`, `TimerControlled`, `PowerThrottled`, `EcoMode`, `GuestMode`) patterns.
- **`SmartHomeTestRunner.java`**: Assertion test suite validating power shedding, device access controls, and power consumption math.

### Compile & Run
```bash
javac *.java
java SmartHome
```
