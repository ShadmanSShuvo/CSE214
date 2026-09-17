import React, { useState } from 'react';
import type { Order } from '../types';
import { formatReceipt, generateJavaCode } from '../services/foodFlowEngine';
import { X, Copy, Download, Code, CheckCircle } from 'lucide-react';

interface ReceiptModalProps {
  order: Order | null;
  onClose: () => void;
  onViewPattern: () => void;
}

export const ReceiptModal: React.FC<ReceiptModalProps> = ({ order, onClose, onViewPattern }) => {
  if (!order) return null;

  const [copied, setCopied] = useState<boolean>(false);
  const [activeTab, setActiveTab] = useState<'receipt' | 'code'>('receipt');

  const receiptText = formatReceipt(order);
  const javaCode = generateJavaCode(order);

  const handleCopy = () => {
    const textToCopy = activeTab === 'receipt' ? receiptText : javaCode;
    navigator.clipboard.writeText(textToCopy);
    setCopied(true);
    setTimeout(() => setCopied(false), 2000);
  };

  const handleDownload = () => {
    const element = document.createElement('a');
    const file = new Blob([receiptText], { type: 'text/plain' });
    element.href = URL.createObjectURL(file);
    element.download = `Receipt-${order.orderId}.txt`;
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-content text-left max-w-2xl" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>
          <X size={20} />
        </button>

        <div className="flex items-center gap-3 mb-4">
          <div className="w-10 h-10 rounded-full bg-emerald-500/20 text-emerald-400 flex items-center justify-center">
            <CheckCircle size={24} />
          </div>
          <div>
            <h2 className="modal-title m-0">Order Placed Successfully!</h2>
            <p className="text-xs text-muted">Order ID: {order.orderId} constructed via Order.Builder</p>
          </div>
        </div>

        <div className="modal-subnav">
          <button
            className={`subnav-btn ${activeTab === 'receipt' ? 'active' : ''}`}
            onClick={() => setActiveTab('receipt')}
          >
            Formatted Receipt
          </button>
          <button
            className={`subnav-btn ${activeTab === 'code' ? 'active' : ''}`}
            onClick={() => setActiveTab('code')}
          >
            <Code size={14} /> Java Builder Code
          </button>
        </div>

        {activeTab === 'receipt' ? (
          <pre className="receipt-monospaced">{receiptText}</pre>
        ) : (
          <pre className="code-monospaced">{javaCode}</pre>
        )}

        <div className="modal-footer flex justify-between items-center">
          <button className="btn-secondary" onClick={onViewPattern}>
            <Code size={16} /> Inspect Pattern Visualizer
          </button>

          <div className="flex gap-2">
            <button className="btn-secondary" onClick={handleDownload}>
              <Download size={16} /> Download .txt
            </button>
            <button className="btn-primary" onClick={handleCopy}>
              <Copy size={16} /> {copied ? 'Copied!' : 'Copy'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
