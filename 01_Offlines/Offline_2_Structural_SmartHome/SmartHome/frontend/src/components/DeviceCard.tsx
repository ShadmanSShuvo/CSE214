import React, { useState } from 'react';
import {
  type SmartDevice,
  AccessRestricted,
  TimerControlled,
  PowerThrottled,
} from '../domain/SmartHomeDomain';
import { PinModal } from './PinModal';

interface DeviceCardProps {
  device: SmartDevice;
  onUpdate: () => void;
  onRemove: (id: string) => void;
  onDecorate: (device: SmartDevice) => void;
}

export const DeviceCard: React.FC<DeviceCardProps> = ({
  device,
  onUpdate,
  onRemove,
  onDecorate,
}) => {
  const [isPinModalOpen, setIsPinModalOpen] = useState(false);

  // Helper to unwrap & extract decorator states
  const getDecoratorChain = (d: SmartDevice) => {
    let current: SmartDevice | null = d;
    let accessRestricted: AccessRestricted | null = null;
    let timerControlled: TimerControlled | null = null;
    let powerThrottled: PowerThrottled | null = null;

    while (current) {
      if (current instanceof AccessRestricted) {
        if (!accessRestricted) accessRestricted = current;
        current = current.wrapped;
      } else if (current instanceof TimerControlled) {
        if (!timerControlled) timerControlled = current;
        current = current.wrapped;
      } else if (current instanceof PowerThrottled) {
        if (!powerThrottled) powerThrottled = current;
        current = current.wrapped;
      } else {
        break; // Reached raw leaf
      }
    }
    return { accessRestricted, timerControlled, powerThrottled, leaf: current };
  };

  const { accessRestricted, timerControlled, powerThrottled } = getDecoratorChain(device);

  const isActive = device.isActive();
  const statusStr = device.getStatus();
  const powerUsage = device.getPowerUsage();

  const getDeviceIcon = (type: string) => {
    switch (type) {
      case 'SmartLight':
        return '💡';
      case 'SmartThermostat':
        return '🌡️';
      case 'SmartSpeaker':
        return '🔊';
      default:
        return '🔌';
    }
  };

  const handleToggle = () => {
    if (accessRestricted && accessRestricted.locked) {
      setIsPinModalOpen(true);
      return;
    }

    if (isActive) {
      device.deactivate();
    } else {
      device.activate();
    }
    onUpdate();
  };

  const handleUnlockPin = (pin: number) => {
    if (accessRestricted) {
      const ok = accessRestricted.unlock(pin);
      if (ok) {
        onUpdate();
      }
      return ok;
    }
    return false;
  };

  const handleTriggerTimerExpiry = () => {
    if (timerControlled) {
      timerControlled.simulateTimerExpiry();
      onUpdate();
    }
  };

  return (
    <>
      <div className={`device-tile ${isActive ? 'active' : ''} ${accessRestricted?.locked ? 'locked' : ''}`}>
        <div className="device-info">
          <div className="device-icon-box">{getDeviceIcon(device.getDeviceType())}</div>
          <div className="device-details">
            <div className="device-name">
              <span>{device.name}</span>
              {accessRestricted && (
                <span className={`badge ${accessRestricted.locked ? 'badge-locked' : 'badge-composite'}`}>
                  {accessRestricted.locked ? '🔒 Locked' : '🔓 Unlocked'}
                </span>
              )}
              {timerControlled?.timerRunning && (
                <span className="badge badge-decorator">⏱️ {timerControlled.remainingSeconds}s</span>
              )}
              {powerThrottled && (
                <span className="badge badge-eco">⚡ Cap {powerThrottled.powerCap}W</span>
              )}
            </div>
            <div className="device-status-text">
              {statusStr} | <strong style={{ color: isActive ? 'var(--accent-emerald)' : 'var(--text-muted)' }}>{powerUsage}W</strong>
            </div>
          </div>
        </div>

        <div className="device-controls">
          {accessRestricted?.locked && (
            <button
              className="btn btn-warning"
              style={{ padding: '0.35rem 0.65rem', fontSize: '0.78rem' }}
              onClick={() => setIsPinModalOpen(true)}
            >
              Enter PIN
            </button>
          )}

          {timerControlled?.timerRunning && (
            <button
              className="btn btn-secondary"
              style={{ padding: '0.35rem 0.65rem', fontSize: '0.78rem', color: 'var(--accent-amber)' }}
              title="Expire Timer Now"
              onClick={handleTriggerTimerExpiry}
            >
              Expire ⌛
            </button>
          )}

          <label className="switch">
            <input type="checkbox" checked={isActive} onChange={handleToggle} />
            <span className="slider"></span>
          </label>

          <button
            className="btn btn-secondary"
            style={{ padding: '0.35rem 0.5rem', fontSize: '0.85rem' }}
            title="Decorate / Upgrade Device"
            onClick={() => onDecorate(device)}
          >
            ⚙️
          </button>

          <button
            className="btn btn-secondary"
            style={{ padding: '0.35rem 0.5rem', fontSize: '0.85rem', color: 'var(--accent-rose)' }}
            title="Remove Device"
            onClick={() => onRemove(device.id)}
          >
            🗑️
          </button>
        </div>
      </div>

      <PinModal
        title="Access Restricted Device"
        targetName={device.name}
        isOpen={isPinModalOpen}
        onClose={() => setIsPinModalOpen(false)}
        onUnlock={handleUnlockPin}
      />
    </>
  );
};
