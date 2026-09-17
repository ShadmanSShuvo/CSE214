import React, { useState } from 'react';
import {
  type SmartDevice,
  CompositeDevice,
  Room,
  SmartLight,
  SmartThermostat,
  SmartSpeaker,
  AccessRestricted,
  TimerControlled,
  PowerThrottled,
  EcoMode,
  GuestMode,
} from '../domain/SmartHomeDomain';

export type BuilderMode =
  | 'ADD_ROOM'
  | 'ADD_DEVICE'
  | 'DECORATE_DEVICE'
  | 'DECORATE_ROOM';

interface BuilderModalProps {
  isOpen: boolean;
  mode: BuilderMode;
  targetRoom?: CompositeDevice | null;
  targetDevice?: SmartDevice | null;
  targetRoomEntity?: SmartDevice | null;
  onClose: () => void;
  onAddRoom: (room: Room) => void;
  onAddDevice: (room: CompositeDevice, device: SmartDevice) => void;
  onReplaceDevice: (oldDevice: SmartDevice, newDevice: SmartDevice) => void;
  onReplaceRoom: (oldRoom: SmartDevice, newRoom: SmartDevice) => void;
}

export const BuilderModal: React.FC<BuilderModalProps> = ({
  isOpen,
  mode,
  targetRoom,
  targetDevice,
  targetRoomEntity,
  onClose,
  onAddRoom,
  onAddDevice,
  onReplaceDevice,
  onReplaceRoom,
}) => {
  // Add Room State
  const [roomName, setRoomName] = useState('Living Room');

  // Add Device State
  const [deviceType, setDeviceType] = useState<'SmartLight' | 'SmartThermostat' | 'SmartSpeaker'>('SmartLight');
  const [deviceName, setDeviceName] = useState('');

  // Decorator State
  const [decoratorType, setDecoratorType] = useState<'PIN' | 'TIMER' | 'THROTTLE' | 'ECO' | 'GUEST'>('PIN');
  const [pinValue, setPinValue] = useState('1234');
  const [timerSeconds, setTimerSeconds] = useState('60');
  const [powerCap, setPowerCap] = useState('80');
  const [ecoBudget, setEcoBudget] = useState('100');
  const [guestAllowed, setGuestAllowed] = useState<{ [key: string]: boolean }>({
    SmartLight: true,
    SmartSpeaker: true,
    SmartThermostat: false,
  });

  if (!isOpen) return null;

  const handleAddRoomSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!roomName.trim()) return;
    const newRoom = new Room(roomName.trim());
    onAddRoom(newRoom);
    onClose();
  };

  const handleAddDeviceSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!targetRoom) return;

    let dev: SmartDevice;
    const name = deviceName.trim() || deviceType;
    if (deviceType === 'SmartLight') {
      dev = new SmartLight(name);
    } else if (deviceType === 'SmartThermostat') {
      dev = new SmartThermostat(name);
    } else {
      dev = new SmartSpeaker(name);
    }

    onAddDevice(targetRoom, dev);
    setDeviceName('');
    onClose();
  };

  const handleDecorateDeviceSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!targetDevice) return;

    let wrapped: SmartDevice = targetDevice;

    if (decoratorType === 'PIN') {
      const pin = parseInt(pinValue, 10) || 1234;
      wrapped = new AccessRestricted(targetDevice, pin);
    } else if (decoratorType === 'TIMER') {
      const secs = parseInt(timerSeconds, 10) || 60;
      wrapped = new TimerControlled(targetDevice, secs);
    } else if (decoratorType === 'THROTTLE') {
      const cap = parseFloat(powerCap) || 80;
      wrapped = new PowerThrottled(targetDevice, cap);
    }

    onReplaceDevice(targetDevice, wrapped);
    onClose();
  };

  const handleDecorateRoomSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!targetRoomEntity) return;

    // Find the base composite room
    let current: SmartDevice | null = targetRoomEntity;
    let baseComposite: CompositeDevice | null = null;
    while (current) {
      if (current instanceof CompositeDevice) {
        baseComposite = current;
        break;
      }
      if ('wrapped' in current) {
        current = (current as any).wrapped;
      } else {
        break;
      }
    }

    if (!baseComposite) return;

    let wrappedRoom: SmartDevice = targetRoomEntity;

    if (decoratorType === 'ECO') {
      const budget = parseFloat(ecoBudget) || 100;
      wrappedRoom = new EcoMode(baseComposite, budget);
    } else if (decoratorType === 'GUEST') {
      const allowed = Object.keys(guestAllowed).filter((k) => guestAllowed[k]);
      wrappedRoom = new GuestMode(baseComposite, allowed);
    } else if (decoratorType === 'PIN') {
      const pin = parseInt(pinValue, 10) || 1234;
      wrappedRoom = new AccessRestricted(targetRoomEntity, pin);
    } else if (decoratorType === 'TIMER') {
      const secs = parseInt(timerSeconds, 10) || 3600;
      wrappedRoom = new TimerControlled(targetRoomEntity, secs);
    }

    onReplaceRoom(targetRoomEntity, wrappedRoom);
    onClose();
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h3 className="modal-title">
            {mode === 'ADD_ROOM' && '➕ Add New Room'}
            {mode === 'ADD_DEVICE' && `➕ Add Device to ${targetRoom?.name}`}
            {mode === 'DECORATE_DEVICE' && `⚙️ Decorate Device (${targetDevice?.name})`}
            {mode === 'DECORATE_ROOM' && `🛡️ Decorate Room (${targetRoomEntity?.name})`}
          </h3>
          <button className="close-btn" onClick={onClose}>✕</button>
        </div>

        {mode === 'ADD_ROOM' && (
          <form onSubmit={handleAddRoomSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Room Name</label>
              <input
                type="text"
                className="form-input"
                value={roomName}
                onChange={(e) => setRoomName(e.target.value)}
                placeholder="e.g. Master Bedroom, Kitchen, Garage"
                required
              />
            </div>
            <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
              Create Room
            </button>
          </form>
        )}

        {mode === 'ADD_DEVICE' && (
          <form onSubmit={handleAddDeviceSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Device Type</label>
              <select
                className="form-select"
                value={deviceType}
                onChange={(e: any) => setDeviceType(e.target.value)}
              >
                <option value="SmartLight">SmartLight (10W)</option>
                <option value="SmartThermostat">SmartThermostat (150W)</option>
                <option value="SmartSpeaker">SmartSpeaker (5W)</option>
              </select>
            </div>

            <div className="form-group">
              <label className="form-label">Custom Device Name (Optional)</label>
              <input
                type="text"
                className="form-input"
                value={deviceName}
                onChange={(e) => setDeviceName(e.target.value)}
                placeholder={`Default: ${deviceType}`}
              />
            </div>

            <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
              Add Device
            </button>
          </form>
        )}

        {mode === 'DECORATE_DEVICE' && (
          <form onSubmit={handleDecorateDeviceSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Select Structural Decorator</label>
              <select
                className="form-select"
                value={decoratorType}
                onChange={(e: any) => setDecoratorType(e.target.value)}
              >
                <option value="PIN">AccessRestricted (PIN Lock)</option>
                <option value="TIMER">TimerControlled (Auto-Off Timer)</option>
                <option value="THROTTLE">PowerThrottled (Wattage Cap)</option>
              </select>
            </div>

            {decoratorType === 'PIN' && (
              <div className="form-group">
                <label className="form-label">Security PIN (4 digits)</label>
                <input
                  type="number"
                  className="form-input"
                  value={pinValue}
                  onChange={(e) => setPinValue(e.target.value)}
                  required
                />
              </div>
            )}

            {decoratorType === 'TIMER' && (
              <div className="form-group">
                <label className="form-label">Auto-off Timer (Seconds)</label>
                <input
                  type="number"
                  className="form-input"
                  value={timerSeconds}
                  onChange={(e) => setTimerSeconds(e.target.value)}
                  required
                />
              </div>
            )}

            {decoratorType === 'THROTTLE' && (
              <div className="form-group">
                <label className="form-label">Power Cap (Watts Limit)</label>
                <input
                  type="number"
                  className="form-input"
                  value={powerCap}
                  onChange={(e) => setPowerCap(e.target.value)}
                  required
                />
              </div>
            )}

            <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
              Wrap Device with Decorator
            </button>
          </form>
        )}

        {mode === 'DECORATE_ROOM' && (
          <form onSubmit={handleDecorateRoomSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
            <div className="form-group">
              <label className="form-label">Select Room Decorator</label>
              <select
                className="form-select"
                value={decoratorType}
                onChange={(e: any) => setDecoratorType(e.target.value)}
              >
                <option value="ECO">EcoMode (Watt Budget & Power Shedding)</option>
                <option value="GUEST">GuestMode (Device Type Restriction)</option>
                <option value="PIN">AccessRestricted (PIN Lock entire room)</option>
                <option value="TIMER">TimerControlled (Auto-Off entire room)</option>
              </select>
            </div>

            {decoratorType === 'ECO' && (
              <div className="form-group">
                <label className="form-label">Max Power Budget (Watts)</label>
                <input
                  type="number"
                  className="form-input"
                  value={ecoBudget}
                  onChange={(e) => setEcoBudget(e.target.value)}
                  required
                />
                <span style={{ fontSize: '0.78rem', color: 'var(--text-secondary)' }}>
                  If room power exceeds budget on activation, devices are shed in reverse order until within budget.
                </span>
              </div>
            )}

            {decoratorType === 'GUEST' && (
              <div className="form-group">
                <label className="form-label">Allowed Device Types</label>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                  {['SmartLight', 'SmartSpeaker', 'SmartThermostat'].map((type) => (
                    <label key={type} style={{ display: 'flex', alignItems: 'center', gap: '0.5rem', fontSize: '0.9rem' }}>
                      <input
                        type="checkbox"
                        checked={guestAllowed[type] || false}
                        onChange={(e) =>
                          setGuestAllowed({ ...guestAllowed, [type]: e.target.checked })
                        }
                      />
                      {type}
                    </label>
                  ))}
                </div>
              </div>
            )}

            {decoratorType === 'PIN' && (
              <div className="form-group">
                <label className="form-label">Security PIN (4 digits)</label>
                <input
                  type="number"
                  className="form-input"
                  value={pinValue}
                  onChange={(e) => setPinValue(e.target.value)}
                  required
                />
              </div>
            )}

            {decoratorType === 'TIMER' && (
              <div className="form-group">
                <label className="form-label">Auto-off Timer (Seconds)</label>
                <input
                  type="number"
                  className="form-input"
                  value={timerSeconds}
                  onChange={(e) => setTimerSeconds(e.target.value)}
                  required
                />
              </div>
            )}

            <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
              Apply Room Decorator
            </button>
          </form>
        )}
      </div>
    </div>
  );
};
