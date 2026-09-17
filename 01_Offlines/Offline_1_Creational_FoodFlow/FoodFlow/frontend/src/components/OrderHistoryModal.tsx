import React from 'react';
import type { Order } from '../types';
import { History, Eye, Calendar, User, MapPin } from 'lucide-react';

interface OrderHistoryModalProps {
  orders: Order[];
  onSelectOrder: (order: Order) => void;
}

export const OrderHistoryModal: React.FC<OrderHistoryModalProps> = ({ orders, onSelectOrder }) => {
  return (
    <div className="history-container">
      <div className="flex items-center gap-2 mb-4">
        <History className="text-amber" size={24} />
        <h2 className="section-title m-0">Completed Orders ({orders.length})</h2>
      </div>

      {orders.length === 0 ? (
        <div className="empty-state">
          <p className="text-muted">No orders placed yet. Build an order in the Cart tab!</p>
        </div>
      ) : (
        <div className="space-y-3">
          {orders.map((order) => (
            <div key={order.orderId} className="history-card">
              <div className="flex justify-between items-start">
                <div>
                  <div className="flex items-center gap-2">
                    <span className="font-bold text-amber">{order.orderId}</span>
                    <span className="badge">{order.deliveryType}</span>
                    <span className="badge text-xs">{order.paymentMethod}</span>
                  </div>
                  <div className="flex items-center gap-4 text-xs text-muted mt-1">
                    <span className="flex items-center gap-1">
                      <User size={12} /> {order.customerName} ({order.phone})
                    </span>
                    <span className="flex items-center gap-1">
                      <Calendar size={12} /> {new Date(order.createdAt).toLocaleTimeString()}
                    </span>
                  </div>
                </div>

                <div className="text-right">
                  <div className="text-lg font-bold text-emerald-400">৳{order.total.toFixed(2)}</div>
                  <button
                    className="btn-secondary compact mt-1"
                    onClick={() => onSelectOrder(order)}
                  >
                    <Eye size={12} /> View Receipt
                  </button>
                </div>
              </div>

              {order.deliveryAddress && (
                <div className="flex items-center gap-1 text-xs text-slate-400 mt-2">
                  <MapPin size={12} /> {order.deliveryAddress}
                </div>
              )}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
