import React, { useState } from 'react';

interface PinModalProps {
  title: string;
  targetName: string;
  isOpen: boolean;
  onClose: () => void;
  onUnlock: (pin: number) => boolean;
}

export const PinModal: React.FC<PinModalProps> = ({
  title,
  targetName,
  isOpen,
  onClose,
  onUnlock,
}) => {
  const [pinInput, setPinInput] = useState('');
  const [errorMsg, setErrorMsg] = useState('');

  if (!isOpen) return null;

  const handleKeyPress = (num: string) => {
    if (pinInput.length < 4) {
      setPinInput((prev) => prev + num);
      setErrorMsg('');
    }
  };

  const handleClear = () => {
    setPinInput('');
    setErrorMsg('');
  };

  const handleSubmit = () => {
    const numericPin = parseInt(pinInput, 10);
    if (isNaN(numericPin)) {
      setErrorMsg('Invalid PIN format');
      return;
    }

    const success = onUnlock(numericPin);
    if (success) {
      setPinInput('');
      setErrorMsg('');
      onClose();
    } else {
      setErrorMsg('Access Denied — Incorrect PIN!');
      setPinInput('');
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <div>
            <h3 className="modal-title">{title}</h3>
            <p style={{ fontSize: '0.85rem', color: 'var(--text-secondary)' }}>
              Enter security PIN to unlock <strong style={{ color: 'var(--accent-cyan)' }}>{targetName}</strong>
            </p>
          </div>
          <button className="close-btn" onClick={onClose}>✕</button>
        </div>

        <div className="pin-display">
          {pinInput.padEnd(4, '•')}
        </div>

        {errorMsg && (
          <p style={{ color: 'var(--accent-rose)', fontSize: '0.85rem', textAlign: 'center', fontWeight: 600 }}>
            ⚠️ {errorMsg}
          </p>
        )}

        <div className="keypad">
          {['1', '2', '3', '4', '5', '6', '7', '8', '9', 'C', '0', '✓'].map((key) => {
            if (key === 'C') {
              return (
                <button key={key} className="keypad-btn" style={{ color: 'var(--accent-rose)' }} onClick={handleClear}>
                  C
                </button>
              );
            }
            if (key === '✓') {
              return (
                <button key={key} className="keypad-btn" style={{ color: 'var(--accent-emerald)' }} onClick={handleSubmit}>
                  ✓
                </button>
              );
            }
            return (
              <button key={key} className="keypad-btn" onClick={() => handleKeyPress(key)}>
                {key}
              </button>
            );
          })}
        </div>

        <button className="btn btn-secondary" style={{ width: '100%' }} onClick={onClose}>
          Cancel
        </button>
      </div>
    </div>
  );
};
