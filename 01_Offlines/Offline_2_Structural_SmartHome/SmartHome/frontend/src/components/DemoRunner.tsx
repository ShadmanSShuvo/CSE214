import React, { useState } from 'react';
import {
  Home,
  Room,
  SmartLight,
  SmartThermostat,
  SmartSpeaker,
  AccessRestricted,
  TimerControlled,
  PowerThrottled,
  EcoMode,
  GuestMode,
  prepareForNight,
} from '../domain/SmartHomeDomain';

type DemoType = 'DEMO_A' | 'DEMO_B' | 'DEMO_C' | 'DEMO_D' | 'DEMO_E' | 'DEMO_F';

export const DemoRunner: React.FC = () => {
  const [selectedDemo, setSelectedDemo] = useState<DemoType>('DEMO_A');
  const [logs, setLogs] = useState<string[]>([]);

  const appendLog = (msg: string) => {
    setLogs((prev) => [...prev, msg]);
  };

  const runDemoA = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO A: Home Overview");
    appendLog("=======================================================");

    const living = new Room("Living Room");
    living.addDevice(new SmartLight("Smart Light"));

    const bedroom = new Room("Bedroom");
    bedroom.addDevice(new SmartThermostat("Smart Thermostat"));

    const home = new Home("My Home");
    home.addRoom(living);
    home.addRoom(bedroom);

    appendLog("Before activation:");
    appendLog(home.getStatus());
    appendLog(`Power: ${home.getPowerUsage()}W`);

    home.activate();
    appendLog("\nAfter activation:");
    appendLog(home.getStatus());
    appendLog(`Power: ${home.getPowerUsage()}W`);
  };

  const runDemoB = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO B: AccessRestricted + TimerControlled");
    appendLog("=======================================================");

    const lockedLight = new AccessRestricted(
      new TimerControlled(new SmartLight("Smart Light"), 60),
      1234
    );

    appendLog("Step 1 — Activate while locked:");
    lockedLight.activate();
    appendLog(`  Status: ${lockedLight.getStatus()}`);
    appendLog(`  Power:  ${lockedLight.getPowerUsage()}W`);

    appendLog("\nStep 2 — Wrong PIN (0000):");
    lockedLight.unlock(0);
    lockedLight.activate();
    appendLog(`  Status: ${lockedLight.getStatus()}`);
    appendLog(`  Power:  ${lockedLight.getPowerUsage()}W`);

    appendLog("\nStep 3 — Correct PIN (1234), activate:");
    lockedLight.unlock(1234);
    lockedLight.activate();
    appendLog(`  Status: ${lockedLight.getStatus()}`);
    appendLog(`  Power:  ${lockedLight.getPowerUsage()}W`);

    appendLog("\nStep 4 — Timer expires:");
    const timer = lockedLight.wrapped as TimerControlled;
    timer.simulateTimerExpiry();
    appendLog("    >> Timer expired — auto-deactivating.");
    appendLog(`  Status: ${lockedLight.getStatus()}`);
    appendLog(`  Power:  ${lockedLight.getPowerUsage()}W`);
  };

  const runDemoC = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO C: EcoMode (budget = 100W)");
    appendLog("=======================================================");

    const office = new Room("Office");
    office.addDevice(new SmartLight("Smart Light 1"));
    office.addDevice(new SmartLight("Smart Light 2"));
    office.addDevice(new SmartThermostat("Smart Thermostat (150W)"));

    const ecoOffice = new EcoMode(office, 100);

    appendLog("Activating with EcoMode:");
    ecoOffice.activate();
    appendLog("\n" + ecoOffice.getStatus());
    appendLog(`Power: ${ecoOffice.getPowerUsage()}W`);
    appendLog("\nNotice: Thermostat (150W) was shed because adding it exceeded 100W budget!");
  };

  const runDemoD = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO D: Order Sensitivity (Throttled vs Raw Eco)");
    appendLog("=======================================================");

    // Setup 1
    const room1 = new Room("Lab-1");
    room1.addDevice(new SmartLight("Smart Light 1"));
    room1.addDevice(new SmartLight("Smart Light 2"));
    room1.addDevice(new PowerThrottled(new SmartThermostat("Smart Thermostat"), 80));
    const ecoRoom1 = new EcoMode(room1, 100);

    appendLog("Setup 1: Throttled SmartThermostat (80W) + EcoMode(100W)");
    ecoRoom1.activate();
    appendLog(ecoRoom1.getStatus());
    appendLog(`Power: ${ecoRoom1.getPowerUsage()}W`);

    // Setup 2
    const room2 = new Room("Lab-2");
    room2.addDevice(new SmartLight("Smart Light 1"));
    room2.addDevice(new SmartLight("Smart Light 2"));
    room2.addDevice(new SmartThermostat("Smart Thermostat"));
    const ecoRoom2 = new EcoMode(room2, 100);

    appendLog("\nSetup 2: Raw SmartThermostat (150W) + EcoMode(100W)");
    ecoRoom2.activate();
    appendLog(ecoRoom2.getStatus());
    appendLog(`Power: ${ecoRoom2.getPowerUsage()}W`);
  };

  const runDemoE = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO E: GuestMode + Mixed Enhancements");
    appendLog("=======================================================");

    const guest = new Room("Guest Room");
    guest.addDevice(new SmartSpeaker("Smart Speaker"));
    guest.addDevice(new AccessRestricted(new SmartThermostat("Smart Thermostat"), 9999));
    guest.addDevice(new TimerControlled(new SmartLight("Smart Light"), 120));

    const allowed = ['SmartLight', 'SmartSpeaker'];
    const guestRoom = new GuestMode(guest, allowed);

    appendLog("Activating GuestMode room:");
    guestRoom.activate();
    appendLog("\n" + guestRoom.getStatus());
    appendLog(`Guest-visible power: ${guestRoom.getPowerUsage()}W`);
  };

  const runDemoF = () => {
    setLogs([]);
    appendLog("=======================================================");
    appendLog("  DEMO F: prepareForNight wraps a Room");
    appendLog("=======================================================");

    const kids = new Room("Kids Room");
    kids.addDevice(new SmartLight("Smart Light"));
    kids.addDevice(new SmartSpeaker("Smart Speaker"));
    kids.addDevice(new SmartThermostat("Smart Thermostat"));

    const night = prepareForNight(kids, 0, 3600);

    appendLog("Step 1 — Activate while locked (nothing happens):");
    night.activate();
    appendLog(`  Status:\n${night.getStatus()}`);
    appendLog(`  Power: ${night.getPowerUsage()}W`);

    appendLog("\nStep 2 — Unlock and activate:");
    const lock = night as AccessRestricted;
    lock.unlock(0);
    night.activate();
    appendLog(`  Status:\n${night.getStatus()}`);
    appendLog(`  Power: ${night.getPowerUsage()}W`);

    appendLog("\nStep 3 — Timer expires (entire room shuts off):");
    const timer = lock.wrapped as TimerControlled;
    timer.simulateTimerExpiry();
    appendLog("    >> Timer expired — auto-deactivating.");
    appendLog(`  Status:\n${night.getStatus()}`);
    appendLog(`  Power: ${night.getPowerUsage()}W`);

    appendLog("\nStep 4 — Add to Home:");
    const home = new Home("Night Home");
    home.addRoom(night);
    appendLog(`  Home power: ${home.getPowerUsage()}W`);
  };

  const handleRun = (demo: DemoType) => {
    setSelectedDemo(demo);
    switch (demo) {
      case 'DEMO_A': runDemoA(); break;
      case 'DEMO_B': runDemoB(); break;
      case 'DEMO_C': runDemoC(); break;
      case 'DEMO_D': runDemoD(); break;
      case 'DEMO_E': runDemoE(); break;
      case 'DEMO_F': runDemoF(); break;
    }
  };

  return (
    <div className="demo-container">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
        <div>
          <h2 style={{ fontSize: '1.25rem', fontWeight: 700, color: 'var(--text-primary)' }}>
            🧪 Structural Design Pattern Demos
          </h2>
          <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
            Interactive scenario executions matching backend test suites in SmartHome.java
          </p>
        </div>

        <div className="demo-selector">
          {(['DEMO_A', 'DEMO_B', 'DEMO_C', 'DEMO_D', 'DEMO_E', 'DEMO_F'] as DemoType[]).map((d) => (
            <button
              key={d}
              className={`btn ${selectedDemo === d ? 'btn-primary' : 'btn-secondary'}`}
              style={{ padding: '0.4rem 0.85rem', fontSize: '0.82rem' }}
              onClick={() => handleRun(d)}
            >
              {d.replace('_', ' ')}
            </button>
          ))}
        </div>
      </div>

      {logs.length === 0 ? (
        <div style={{ padding: '2rem', textAlign: 'center', color: 'var(--text-muted)' }}>
          Click any demo button above to run scenario execution.
        </div>
      ) : (
        <div className="demo-log">
          {logs.map((line, idx) => (
            <div key={idx}>{line}</div>
          ))}
        </div>
      )}
    </div>
  );
};
