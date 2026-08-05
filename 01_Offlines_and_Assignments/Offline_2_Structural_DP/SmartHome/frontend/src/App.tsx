import React, { useState, useEffect, useCallback } from 'react';
import {
  Home,
  Room,
  type SmartDevice,
  CompositeDevice,
  SmartLight,
  SmartThermostat,
  SmartSpeaker,
  AccessRestricted,
  TimerControlled,
  PowerThrottled,
  EcoMode,
} from './domain/SmartHomeDomain';
import { Header } from './components/Header';
import { RoomCard } from './components/RoomCard';
import { BuilderModal, type BuilderMode } from './components/BuilderModal';
import { DemoRunner } from './components/DemoRunner';

export const App: React.FC = () => {
  // Main Home Composite object
  const [home, setHome] = useState<Home>(() => createInitialSetup());
  const [currentView, setCurrentView] = useState<'DASHBOARD' | 'DEMOS'>('DASHBOARD');
  const [, setTickCounter] = useState(0);

  // Modal State
  const [isBuilderOpen, setIsBuilderOpen] = useState(false);
  const [builderMode, setBuilderMode] = useState<BuilderMode>('ADD_ROOM');
  const [targetRoom, setTargetRoom] = useState<CompositeDevice | null>(null);
  const [targetDevice, setTargetDevice] = useState<SmartDevice | null>(null);
  const [targetRoomEntity, setTargetRoomEntity] = useState<SmartDevice | null>(null);

  // Function to build initial demo setup
  function createInitialSetup(): Home {
    const mainHome = new Home("My Smart Home");

    // Room 1: Living Room
    const living = new Room("Living Room");
    living.addDevice(new SmartLight("Main Ceiling Light"));
    living.addDevice(new SmartSpeaker("Ambient Speaker"));
    const ecoLiving = new EcoMode(living, 100);

    // Room 2: Master Bedroom
    const bedroom = new Room("Master Bedroom");
    bedroom.addDevice(new TimerControlled(new SmartLight("Bedside Lamp"), 30));
    bedroom.addDevice(new AccessRestricted(new SmartThermostat("Smart Thermostat"), 1234));

    // Room 3: Home Office
    const office = new Room("Home Office");
    office.addDevice(new PowerThrottled(new SmartThermostat("HVAC Unit"), 80));
    office.addDevice(new SmartLight("Desk Lamp"));

    mainHome.addRoom(ecoLiving);
    mainHome.addRoom(bedroom);
    mainHome.addRoom(office);

    return mainHome;
  }

  const forceUpdate = useCallback(() => {
    setTickCounter((prev) => prev + 1);
  }, []);

  // Timer Ticker Loop for TimerControlled devices
  useEffect(() => {
    const interval = setInterval(() => {
      let updated = false;

      // Recursive helper to tick timers
      const tickTimers = (device: SmartDevice) => {
        if (device instanceof TimerControlled) {
          const expired = device.tick();
          if (expired || device.timerRunning) {
            updated = true;
          }
        }
        if ('wrapped' in device) {
          tickTimers((device as any).wrapped);
        }
        if (device instanceof CompositeDevice) {
          for (const child of device.getChildren()) {
            tickTimers(child);
          }
        }
      };

      for (const roomEntity of home.getChildren()) {
        tickTimers(roomEntity);
      }

      if (updated) {
        forceUpdate();
      }
    }, 1000);

    return () => clearInterval(interval);
  }, [home, forceUpdate]);

  // Master Actions
  const handleActivateAll = () => {
    home.activate();
    forceUpdate();
  };

  const handleDeactivateAll = () => {
    home.deactivate();
    forceUpdate();
  };

  const handleResetPreset = () => {
    setHome(createInitialSetup());
    forceUpdate();
  };

  // Add Room
  const handleAddRoom = (newRoom: Room) => {
    home.addRoom(newRoom);
    forceUpdate();
  };

  // Add Device to Room
  const handleAddDevice = (roomComposite: CompositeDevice, device: SmartDevice) => {
    roomComposite.addDevice(device);
    forceUpdate();
  };

  // Replace Device (when decorated)
  const handleReplaceDevice = (oldDevice: SmartDevice, newDevice: SmartDevice) => {
    const replaceInContainer = (container: CompositeDevice): boolean => {
      const children = container.getChildren();
      for (let i = 0; i < children.length; i++) {
        if (children[i].id === oldDevice.id) {
          children[i] = newDevice;
          return true;
        }
        if (children[i] instanceof CompositeDevice) {
          if (replaceInContainer(children[i] as CompositeDevice)) return true;
        }
      }
      return false;
    };

    for (const roomEntity of home.getChildren()) {
      let current: SmartDevice | null = roomEntity;
      while (current) {
        if (current instanceof CompositeDevice) {
          if (replaceInContainer(current)) {
            forceUpdate();
            return;
          }
          break;
        }
        if ('wrapped' in current) {
          current = (current as any).wrapped;
        } else {
          break;
        }
      }
    }
  };

  // Replace Room (when decorated)
  const handleReplaceRoom = (oldRoomEntity: SmartDevice, newRoomEntity: SmartDevice) => {
    const rooms = home.getChildren();
    for (let i = 0; i < rooms.length; i++) {
      if (rooms[i].id === oldRoomEntity.id) {
        rooms[i] = newRoomEntity;
        forceUpdate();
        return;
      }
    }
  };

  // Remove Room
  const handleRemoveRoom = (roomId: string) => {
    home.removeDevice(roomId);
    forceUpdate();
  };

  // Modal Triggers
  const openAddRoomModal = () => {
    setBuilderMode('ADD_ROOM');
    setIsBuilderOpen(true);
  };

  const openAddDeviceModal = (roomComposite: CompositeDevice) => {
    setTargetRoom(roomComposite);
    setBuilderMode('ADD_DEVICE');
    setIsBuilderOpen(true);
  };

  const openDecorateDeviceModal = (device: SmartDevice) => {
    setTargetDevice(device);
    setBuilderMode('DECORATE_DEVICE');
    setIsBuilderOpen(true);
  };

  const openDecorateRoomModal = (roomEntity: SmartDevice) => {
    setTargetRoomEntity(roomEntity);
    setBuilderMode('DECORATE_ROOM');
    setIsBuilderOpen(true);
  };

  // Calculate System Stats
  const totalPower = home.getPowerUsage();
  const rooms = home.getChildren();

  let activeCount = 0;
  const countActive = (dev: SmartDevice) => {
    if (dev instanceof CompositeDevice) {
      for (const c of dev.getChildren()) countActive(c);
    } else if ('wrapped' in dev) {
      countActive((dev as any).wrapped);
    } else {
      if (dev.isActive()) activeCount++;
    }
  };
  for (const r of rooms) countActive(r);

  return (
    <div className="app-container">
      <Header
        totalPower={totalPower}
        maxEstimatedPower={300}
        activeDevicesCount={activeCount}
        totalRoomsCount={rooms.length}
        currentView={currentView}
        onViewChange={setCurrentView}
        onActivateAll={handleActivateAll}
        onDeactivateAll={handleDeactivateAll}
        onAddRoomModal={openAddRoomModal}
        onLoadPreset={handleResetPreset}
      />

      {currentView === 'DASHBOARD' ? (
        <main className="rooms-grid">
          {rooms.length === 0 ? (
            <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '3rem', color: 'var(--text-muted)' }}>
              No rooms configured in your home yet. Click <strong>+ Add Room</strong> to create your first room.
            </div>
          ) : (
            rooms.map((roomEntity) => (
              <RoomCard
                key={roomEntity.id}
                roomEntity={roomEntity}
                onUpdate={forceUpdate}
                onRemoveRoom={handleRemoveRoom}
                onAddDeviceToRoom={openAddDeviceModal}
                onDecorateRoom={openDecorateRoomModal}
                onDecorateDevice={openDecorateDeviceModal}
              />
            ))
          )}
        </main>
      ) : (
        <DemoRunner />
      )}

      <BuilderModal
        isOpen={isBuilderOpen}
        mode={builderMode}
        targetRoom={targetRoom}
        targetDevice={targetDevice}
        targetRoomEntity={targetRoomEntity}
        onClose={() => setIsBuilderOpen(false)}
        onAddRoom={handleAddRoom}
        onAddDevice={handleAddDevice}
        onReplaceDevice={handleReplaceDevice}
        onReplaceRoom={handleReplaceRoom}
      />
    </div>
  );
};

export default App;
