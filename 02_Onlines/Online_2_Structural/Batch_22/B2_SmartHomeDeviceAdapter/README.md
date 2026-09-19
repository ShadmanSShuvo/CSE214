# Batch 22 Online 2 (B2) - Smart Home Voice Control (Adapter Pattern)

## Problem Statement
A company is developing a **Smart Home Control App** that allows users to control various smart devices using voice commands.

The system already supports devices that follow the standard interface:
```java
public interface SmartDevice {
    void turnOn();
    void turnOff();
}
```
Existing devices (`SmartLight`, `SmartFan`, `SmartAC`) implement `SmartDevice`.

The company wants to integrate third-party smart devices from other manufacturers that do **not** follow `SmartDevice`:
1. **OldSmartBulb**:
   ```java
   public class OldSmartBulb {
       public void powerOn() { }
       public void powerOff() { }
   }
   ```
2. **LegacyHeater**:
   ```java
   public class LegacyHeater {
       public void startHeating() { }
       public void stopHeating() { }
   }
   ```

### Constraints
- The `SmartDevice` interface must not be modified.
- Third-party classes (`OldSmartBulb`, `LegacyHeater`) cannot be changed.
- The app must control all devices uniformly using `turnOn()` and `turnOff()`.
- Adding more third-party devices in the future must be easy.

---

## Design Pattern Analysis

### Pattern Applied: **Adapter Pattern (Object Adapter)**

### Why Adapter?
- **Interface Mismatch**:
  - `OldSmartBulb` provides `powerOn()` / `powerOff()`.
  - `LegacyHeater` provides `startHeating()` / `stopHeating()`.
  - The client application expects `turnOn()` / `turnOff()`.
- **Closed Source / Unmodifiable**: Third-party classes are external and cannot be edited.
- **The Adapters**:
  - `OldSmartBulbAdapter` implements `SmartDevice`, wraps `OldSmartBulb`, maps `turnOn() -> powerOn()`.
  - `LegacyHeaterAdapter` implements `SmartDevice`, wraps `LegacyHeater`, maps `turnOn() -> startHeating()`.

### Pattern Participants
| Role | Class / Interface | Description |
| :--- | :--- | :--- |
| **Target** | `SmartDevice` | App standard interface (`turnOn()`, `turnOff()`). |
| **Adaptees** | `OldSmartBulb`, `LegacyHeater` | Third-party device classes with incompatible methods. |
| **Adapters** | `OldSmartBulbAdapter`, `LegacyHeaterAdapter` | Wrap adaptees and implement `SmartDevice`. |
| **Client** | `B2Adapter` | Iterates over `List<SmartDevice>` and controls all devices uniformly. |

---

## Class Architecture

```
                 <<interface>>
                  SmartDevice
             +turnOn()   +turnOff()
                 ^           ^
                 |           |
        +--------+           +---------------+
        |                                    |
   SmartLight                       OldSmartBulbAdapter  ------>  OldSmartBulb (Adaptee)
  (Native impl)                     -bulb: OldSmartBulb           +powerOn() / +powerOff()
                                    +turnOn() -> powerOn()
                                    +turnOff() -> powerOff()
```

---

## Solution Walkthrough

1. **Adapters**:
   ```java
   class OldSmartBulbAdapter implements SmartDevice {
       private final OldSmartBulb bulb;
       OldSmartBulbAdapter(OldSmartBulb bulb) { this.bulb = bulb; }
       @Override public void turnOn() { bulb.powerOn(); }
       @Override public void turnOff() { bulb.powerOff(); }
   }

   class LegacyHeaterAdapter implements SmartDevice {
       private final LegacyHeater heater;
       LegacyHeaterAdapter(LegacyHeater heater) { this.heater = heater; }
       @Override public void turnOn() { heater.startHeating(); }
       @Override public void turnOff() { heater.stopHeating(); }
   }
   ```
2. **Polymorphic Client Usage**:
   ```java
   List<SmartDevice> devices = new ArrayList<>();
   devices.add(new SmartLight());
   devices.add(new OldSmartBulbAdapter(new OldSmartBulb()));
   devices.add(new LegacyHeaterAdapter(new LegacyHeater()));

   for (SmartDevice d : devices) {
       d.turnOn();
       d.turnOff();
   }
   ```

---

## How to Compile & Run

```bash
cd Batch_22/B2_SmartHomeDeviceAdapter
javac *.java
java B2Adapter
```

### Output
```
Smart Light ON
Smart Light OFF
OldSmartBulb powered on
OldSmartBulb powered off
LegacyHeater started
LegacyHeater stopped
```
