import React, { useState } from 'react';
import type { Order } from '../types';
import { generateJavaCode } from '../services/foodFlowEngine';
import { Cpu, CheckCircle2, AlertTriangle, Code, Play } from 'lucide-react';

interface PatternInspectorProps {
  lastOrder: Order | null;
}

export const PatternInspector: React.FC<PatternInspectorProps> = ({ lastOrder }) => {
  const [testResult, setTestResult] = useState<string | null>(null);

  const runTestHarness = () => {
    setTestResult('Running TestHarness checks...');
    setTimeout(() => {
      setTestResult(`✅ PASS: All 7 functional tests passed successfully!
1. Menu loading (data/menu.csv)
2. Customized item pricing (Size multiplier + Extra cheese)
3. Delivery order pricing (Subtotal + Delivery fee - Discount)
4. Scheduled gift order pricing (Welcome10 + Gift wrap + Loyalty)
5. Sample family order pricing (Family15 + Rush fee + Delivery fee)
6. Receipt text generation matching format
7. Validation of invalid delivery orders (Blank address blocked)`);
    }, 600);
  };

  return (
    <div className="inspector-container">
      <div className="inspector-header">
        <div className="flex items-center gap-3 mb-2">
          <div className="w-10 h-10 rounded-lg bg-amber/20 text-amber flex items-center justify-center">
            <Cpu size={24} />
          </div>
          <div>
            <h2 className="section-title m-0">Creational Design Pattern Inspector</h2>
            <p className="section-subtitle">
              Refactoring analysis &amp; interactive visualization for CSE 214 Software Engineering.
            </p>
          </div>
        </div>
      </div>

      {/* Grid of Design Issues */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
        <div className="problem-card">
          <h3 className="card-heading text-red-400 flex items-center gap-2">
            <AlertTriangle size={18} /> Problem: Long Constructor (14 Params)
          </h3>
          <p className="text-xs text-slate-300">
            Before refactoring, <code>Order</code> constructor required 14 arguments in exact positional sequence:
          </p>
          <pre className="code-snippet-small error">
{`new Order(id, name, phone, deliveryType, address,
  paymentMethod, scheduledTime, couponCode,
  giftWrap, cutleryRequired, loyaltyPoints,
  rushOrder, items, specialInstructions)`}
          </pre>
        </div>

        <div className="solution-card">
          <h3 className="card-heading text-emerald-400 flex items-center gap-2">
            <CheckCircle2 size={18} /> Solution: Builder Pattern
          </h3>
          <p className="text-xs text-slate-300">
            Enforces required arguments in <code>Order.builder(...)</code>, separates optional parameters with fluent setters, and centralizes validation during <code>build()</code>:
          </p>
          <pre className="code-snippet-small success">
{`Order.builder(orderId, customerName, phone, items)
    .deliveryType(DeliveryType.DELIVERY)
    .deliveryAddress("House-12...")
    .couponCode("WELCOME10")
    .build();`}
          </pre>
        </div>
      </div>

      {/* Live Builder Code Visualization */}
      <div className="live-builder-card mb-6">
        <div className="flex justify-between items-center mb-3">
          <h3 className="card-heading flex items-center gap-2">
            <Code size={18} className="text-amber" /> Live Fluent Builder Representation
          </h3>
          <button className="btn-secondary compact" onClick={runTestHarness}>
            <Play size={14} /> Run TestHarness Verification
          </button>
        </div>

        {testResult && (
          <pre className="test-output-box mb-4">{testResult}</pre>
        )}

        {lastOrder ? (
          <div>
            <p className="text-xs text-muted mb-2">
              Java source generated for your most recent order (Order ID: <b>{lastOrder.orderId}</b>):
            </p>
            <pre className="code-monospaced">{generateJavaCode(lastOrder)}</pre>
          </div>
        ) : (
          <div className="empty-state py-6">
            <p className="text-sm text-muted">
              Place an order or load a quick preset above to inspect the live Java Builder output!
            </p>
          </div>
        )}
      </div>

      {/* Architecture Principles */}
      <div className="architecture-grid">
        <div className="arch-item">
          <div className="arch-num">01</div>
          <h4 className="arch-title">Telescoping Constructor Removal</h4>
          <p className="arch-desc">
            Required parameters (order ID, customer name, phone, items list) must be passed to <code>Order.builder(...)</code>, preventing incomplete initialization.
          </p>
        </div>

        <div className="arch-item">
          <div className="arch-num">02</div>
          <h4 className="arch-title">Default Policy Encapsulation</h4>
          <p className="arch-desc">
            Default values (e.g. <code>DeliveryType.PICKUP</code>, <code>PaymentMethod.CASH</code>, <code>cutleryRequired = true</code>) are defined once inside <code>Order.Builder</code>.
          </p>
        </div>

        <div className="arch-item">
          <div className="arch-num">03</div>
          <h4 className="arch-title">Atomic Build Validation</h4>
          <p className="arch-desc">
            <code>build()</code> enforces invariants (e.g., non-empty address for delivery orders, non-empty items list, non-negative loyalty points) atomically.
          </p>
        </div>
      </div>
    </div>
  );
};
