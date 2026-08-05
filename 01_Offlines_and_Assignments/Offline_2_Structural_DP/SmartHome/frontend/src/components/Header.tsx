import React from 'react';

interface HeaderProps {
  totalPower: number;
  maxEstimatedPower: number;
  activeDevicesCount: number;
  totalRoomsCount: number;
  currentView: 'DASHBOARD' | 'DEMOS';
  onViewChange: (view: 'DASHBOARD' | 'DEMOS') => void;
  onActivateAll: () => void;
  onDeactivateAll: () => void;
  onAddRoomModal: () => void;
  onLoadPreset: () => void;
}

export const Header: React.FC<HeaderProps> = ({
  totalPower,
  maxEstimatedPower,
  activeDevicesCount,
  totalRoomsCount,
  currentView,
  onViewChange,
  onActivateAll,
  onDeactivateAll,
  onAddRoomModal,
  onLoadPreset,
}) => {
  const powerPercentage = Math.min(100, Math.round((totalPower / (maxEstimatedPower || 300)) * 100));

  const getPowerColor = (pct: number) => {
    if (pct > 75) return 'var(--accent-rose)';
    if (pct > 40) return 'var(--accent-amber)';
    return 'var(--accent-emerald)';
  };

  return (
    <header className="app-header">
      <div className="header-top">
        <div className="brand-title">
          <div className="brand-icon">🏠</div>
          <div>
            <h1>SmartHome Hub</h1>
            <div className="pattern-badges">
              <span className="badge badge-composite">Composite Pattern</span>
              <span className="badge badge-decorator">Decorator Pattern</span>
              <span className="badge badge-eco">EcoMode</span>
              <span className="badge badge-guest">GuestMode</span>
            </div>
          </div>
        </div>

        <div className="view-tabs">
          <button
            className={`tab-btn ${currentView === 'DASHBOARD' ? 'active' : ''}`}
            onClick={() => onViewChange('DASHBOARD')}
          >
            🎛️ Control Center
          </button>
          <button
            className={`tab-btn ${currentView === 'DEMOS' ? 'active' : ''}`}
            onClick={() => onViewChange('DEMOS')}
          >
            🧪 Design Pattern Demos
          </button>
        </div>
      </div>

      <div className="stats-bar">
        <div className="stat-card">
          <div className="stat-label">
            <span>Total Power Consumption</span>
            <span style={{ color: getPowerColor(powerPercentage), fontWeight: 600 }}>{powerPercentage}% Load</span>
          </div>
          <div className="stat-value">
            {totalPower} <span className="stat-unit">Watts</span>
          </div>
          <div className="power-progress-bg">
            <div
              className="power-progress-fill"
              style={{
                width: `${powerPercentage}%`,
                backgroundColor: getPowerColor(powerPercentage),
              }}
            ></div>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-label">
            <span>Active Devices</span>
            <span>⚡ Real-time</span>
          </div>
          <div className="stat-value">
            {activeDevicesCount} <span className="stat-unit">active</span>
          </div>
        </div>

        <div className="stat-card">
          <div className="stat-label">
            <span>Total Rooms</span>
            <span>🏠 Composite Containers</span>
          </div>
          <div className="stat-value">
            {totalRoomsCount} <span className="stat-unit">rooms</span>
          </div>
        </div>
      </div>

      {currentView === 'DASHBOARD' && (
        <div className="toolbar">
          <div className="btn-group">
            <button className="btn btn-primary" onClick={onAddRoomModal}>
              ➕ Add Room
            </button>
            <button className="btn btn-success" onClick={onActivateAll}>
              ⚡ Activate All
            </button>
            <button className="btn btn-danger" onClick={onDeactivateAll}>
              🌙 Deactivate All
            </button>
          </div>

          <div className="btn-group">
            <button className="btn btn-secondary" onClick={onLoadPreset}>
              🔄 Reset Demo Preset
            </button>
          </div>
        </div>
      )}
    </header>
  );
};
