import React from 'react';
import {
  type SmartDevice,
  CompositeDevice,
  EcoMode,
  GuestMode,
  AccessRestricted,
  TimerControlled,
} from '../domain/SmartHomeDomain';
import { DeviceCard } from './DeviceCard';

interface RoomCardProps {
  roomEntity: SmartDevice;
  onUpdate: () => void;
  onRemoveRoom: (roomId: string) => void;
  onAddDeviceToRoom: (roomComposite: CompositeDevice) => void;
  onDecorateRoom: (roomEntity: SmartDevice) => void;
  onDecorateDevice: (device: SmartDevice) => void;
}

export const RoomCard: React.FC<RoomCardProps> = ({
  roomEntity,
  onUpdate,
  onRemoveRoom,
  onAddDeviceToRoom,
  onDecorateRoom,
  onDecorateDevice,
}) => {
  // Extract room details through decorator layers
  const extractRoomDetails = (entity: SmartDevice) => {
    let current: SmartDevice | null = entity;
    let ecoMode: EcoMode | null = null;
    let guestMode: GuestMode | null = null;
    let accessRestricted: AccessRestricted | null = null;
    let timerControlled: TimerControlled | null = null;
    let compositeRoom: CompositeDevice | null = null;

    while (current) {
      if (current instanceof EcoMode) {
        if (!ecoMode) ecoMode = current;
        current = current.wrapped;
      } else if (current instanceof GuestMode) {
        if (!guestMode) guestMode = current;
        current = current.wrapped;
      } else if (current instanceof AccessRestricted) {
        if (!accessRestricted) accessRestricted = current;
        current = current.wrapped;
      } else if (current instanceof TimerControlled) {
        if (!timerControlled) timerControlled = current;
        current = current.wrapped;
      } else if (current instanceof CompositeDevice) {
        compositeRoom = current;
        break;
      } else {
        break;
      }
    }
    return { ecoMode, guestMode, accessRestricted, timerControlled, compositeRoom };
  };

  const { ecoMode, guestMode, accessRestricted, timerControlled, compositeRoom } =
    extractRoomDetails(roomEntity);

  const isRoomActive = roomEntity.isActive();
  const roomPower = roomEntity.getPowerUsage();
  const children = compositeRoom ? compositeRoom.getChildren() : [];

  const handleToggleRoom = () => {
    if (isRoomActive) {
      roomEntity.deactivate();
    } else {
      roomEntity.activate();
    }
    onUpdate();
  };

  const handleRemoveDevice = (deviceId: string) => {
    if (compositeRoom) {
      compositeRoom.removeDevice(deviceId);
      onUpdate();
    }
  };

  return (
    <div className="room-card">
      <div className="room-header">
        <div className="room-title-area">
          <div className="room-title">
            <span>🏠 {roomEntity.name}</span>
            {ecoMode && <span className="badge badge-eco">🌱 ECO {ecoMode.budget}W</span>}
            {guestMode && <span className="badge badge-guest">👤 GUEST</span>}
            {accessRestricted && (
              <span className={`badge ${accessRestricted.locked ? 'badge-locked' : 'badge-composite'}`}>
                {accessRestricted.locked ? '🔒 Locked' : '🔓 Unlocked'}
              </span>
            )}
            {timerControlled?.timerRunning && (
              <span className="badge badge-decorator">⏱️ {timerControlled.remainingSeconds}s</span>
            )}
          </div>
          <div style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
            Power: <strong style={{ color: 'var(--accent-cyan)' }}>{roomPower}W</strong> | {children.length} Device{children.length !== 1 ? 's' : ''}
          </div>
        </div>

        <div className="room-actions">
          <label className="switch" title="Toggle Room Power">
            <input type="checkbox" checked={isRoomActive} onChange={handleToggleRoom} />
            <span className="slider"></span>
          </label>

          <button
            className="btn btn-secondary"
            style={{ padding: '0.4rem 0.65rem', fontSize: '0.82rem' }}
            title="Decorate Room (Eco, Guest, Lock, Timer)"
            onClick={() => onDecorateRoom(roomEntity)}
          >
            🛡️ Decorate
          </button>

          {compositeRoom && (
            <button
              className="btn btn-primary"
              style={{ padding: '0.4rem 0.65rem', fontSize: '0.82rem' }}
              title="Add Device to Room"
              onClick={() => onAddDeviceToRoom(compositeRoom!)}
            >
              + Device
            </button>
          )}

          <button
            className="btn btn-secondary"
            style={{ padding: '0.4rem 0.5rem', fontSize: '0.82rem', color: 'var(--accent-rose)' }}
            title="Delete Room"
            onClick={() => onRemoveRoom(roomEntity.id)}
          >
            🗑️
          </button>
        </div>
      </div>

      <div className="devices-list">
        {children.length === 0 ? (
          <p style={{ fontSize: '0.85rem', color: 'var(--text-muted)', fontStyle: 'italic', padding: '0.5rem 0' }}>
            No devices in this room yet. Click <strong>+ Device</strong> to add one.
          </p>
        ) : (
          children.map((d) => (
            <DeviceCard
              key={d.id}
              device={d}
              onUpdate={onUpdate}
              onRemove={handleRemoveDevice}
              onDecorate={onDecorateDevice}
            />
          ))
        )}
      </div>
    </div>
  );
};
