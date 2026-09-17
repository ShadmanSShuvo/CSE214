import React, { useState } from 'react';
import type { OrderItem, DeliveryType, PaymentMethod, Order } from '../types';
import { OrderBuilder, CONSTANTS, describeOrderItemOptions } from '../services/foodFlowEngine';
import { ShoppingBag, Trash2, Tag, Gift, Zap, Clock, ShieldCheck, AlertCircle } from 'lucide-react';

interface CartDrawerProps {
  items: OrderItem[];
  onUpdateQuantity: (id: string, newQty: number) => void;
  onRemoveItem: (id: string) => void;
  onClearCart: () => void;
  onOrderSuccess: (order: Order) => void;
}

export const CartDrawer: React.FC<CartDrawerProps> = ({
  items,
  onUpdateQuantity,
  onRemoveItem,
  onClearCart,
  onOrderSuccess,
}) => {
  const [customerName, setCustomerName] = useState('Alice');
  const [phone, setPhone] = useState('01700000000');
  const [deliveryType, setDeliveryType] = useState<DeliveryType>('DELIVERY');
  const [deliveryAddress, setDeliveryAddress] = useState('House-12, Road-7, Dhanmondi');
  const [paymentMethod, setPaymentMethod] = useState<PaymentMethod>('CASH');
  const [scheduledTime, setScheduledTime] = useState<string>('');
  const [couponCode, setCouponCode] = useState<string>('WELCOME10');
  const [giftWrap, setGiftWrap] = useState<boolean>(false);
  const [cutleryRequired, setCutleryRequired] = useState<boolean>(true);
  const [loyaltyPoints, setLoyaltyPoints] = useState<number>(0);
  const [rushOrder, setRushOrder] = useState<boolean>(false);
  const [specialInstructions, setSpecialInstructions] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState<string>('');

  const nextOrderId = `FF-${Math.floor(1000 + Math.random() * 9000)}`;

  // Calculate live preview totals
  const subtotal = items.reduce((sum, item) => sum + item.subtotal, 0);

  let couponDiscount = 0;
  const upperCoupon = couponCode.trim().toUpperCase();
  if (upperCoupon === 'WELCOME10') {
    couponDiscount = subtotal * 0.1;
  } else if (upperCoupon === 'FAMILY15' && subtotal >= 1000) {
    couponDiscount = subtotal * 0.15;
  }

  const loyaltyDiscount = Math.min(loyaltyPoints, 100);
  const totalDiscount = couponDiscount + loyaltyDiscount;

  let serviceCharges = 0;
  if (deliveryType === 'DELIVERY') serviceCharges += CONSTANTS.DELIVERY_FEE;
  if (rushOrder) serviceCharges += CONSTANTS.RUSH_FEE;
  if (giftWrap) serviceCharges += CONSTANTS.GIFT_WRAP_FEE;

  const total = Math.max(0, subtotal + serviceCharges - totalDiscount);

  const handleCheckout = () => {
    setErrorMessage('');
    try {
      const order = new OrderBuilder(nextOrderId, customerName, phone, items)
        .setDeliveryType(deliveryType)
        .setDeliveryAddress(deliveryAddress)
        .setPaymentMethod(paymentMethod)
        .setScheduledTime(scheduledTime || null)
        .setCouponCode(couponCode)
        .setGiftWrap(giftWrap)
        .setCutleryRequired(cutleryRequired)
        .setLoyaltyPointsToRedeem(loyaltyPoints)
        .setRushOrder(rushOrder)
        .setSpecialInstructions(specialInstructions)
        .build();

      onOrderSuccess(order);
    } catch (err: any) {
      setErrorMessage(err.message || 'Validation failed during order building.');
    }
  };

  return (
    <div className="cart-checkout-container">
      <div className="cart-header">
        <div className="flex items-center gap-2">
          <ShoppingBag className="text-amber" size={22} />
          <h2 className="section-title m-0">Your Order Cart</h2>
        </div>
        {items.length > 0 && (
          <button className="text-xs text-muted hover:text-red-400" onClick={onClearCart}>
            Clear Cart
          </button>
        )}
      </div>

      {items.length === 0 ? (
        <div className="empty-cart">
          <ShoppingBag size={48} className="text-muted opacity-40 mb-3" />
          <p className="text-lg font-medium text-slate-300">Your cart is currently empty</p>
          <p className="text-sm text-muted">Browse our menu and add items to begin building your order.</p>
        </div>
      ) : (
        <div className="cart-layout">
          {/* Items Column */}
          <div className="cart-items-section">
            <h3 className="section-subtitle mb-3">Itemized Cart ({items.length})</h3>
            <div className="cart-items-list">
              {items.map((item) => (
                <div key={item.id} className="cart-item-card">
                  <img
                    src={item.menuItem.image}
                    alt={item.menuItem.name}
                    className="cart-item-img"
                  />
                  <div className="cart-item-details">
                    <div className="flex justify-between items-start">
                      <h4 className="cart-item-title">{item.menuItem.name}</h4>
                      <button
                        className="btn-icon-danger"
                        onClick={() => onRemoveItem(item.id)}
                        title="Remove item"
                      >
                        <Trash2 size={16} />
                      </button>
                    </div>
                    <p className="cart-item-desc">{describeOrderItemOptions(item)}</p>

                    <div className="flex justify-between items-center mt-2">
                      <div className="stepper compact">
                        <button
                          className="stepper-btn"
                          onClick={() => onUpdateQuantity(item.id, item.quantity - 1)}
                        >
                          -
                        </button>
                        <span className="stepper-value">{item.quantity}</span>
                        <button
                          className="stepper-btn"
                          onClick={() => onUpdateQuantity(item.id, item.quantity + 1)}
                        >
                          +
                        </button>
                      </div>
                      <span className="cart-item-price">৳{item.subtotal.toFixed(2)}</span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Form & Configuration Column */}
          <div className="checkout-form-section">
            <h3 className="section-subtitle mb-3">Order Configuration (Order.Builder)</h3>

            {errorMessage && (
              <div className="error-alert">
                <AlertCircle size={18} />
                <span>{errorMessage}</span>
              </div>
            )}

            {/* Customer Details */}
            <div className="form-group">
              <label className="input-label">Customer Name *</label>
              <input
                type="text"
                className="text-input"
                value={customerName}
                onChange={(e) => setCustomerName(e.target.value)}
                placeholder="e.g. Alice"
              />
            </div>

            <div className="form-group">
              <label className="input-label">Phone Number *</label>
              <input
                type="text"
                className="text-input"
                value={phone}
                onChange={(e) => setPhone(e.target.value)}
                placeholder="e.g. 01700000000"
              />
            </div>

            {/* Delivery Type */}
            <div className="form-group">
              <label className="input-label">Fulfillment Mode</label>
              <div className="grid grid-cols-2 gap-2">
                <button
                  type="button"
                  className={`btn-toggle ${deliveryType === 'PICKUP' ? 'active' : ''}`}
                  onClick={() => setDeliveryType('PICKUP')}
                >
                  🚶 Pickup
                </button>
                <button
                  type="button"
                  className={`btn-toggle ${deliveryType === 'DELIVERY' ? 'active' : ''}`}
                  onClick={() => setDeliveryType('DELIVERY')}
                >
                  🛵 Delivery (+৳{CONSTANTS.DELIVERY_FEE})
                </button>
              </div>
            </div>

            {deliveryType === 'DELIVERY' && (
              <div className="form-group">
                <label className="input-label">Delivery Address *</label>
                <textarea
                  className="text-input rows-2"
                  value={deliveryAddress}
                  onChange={(e) => setDeliveryAddress(e.target.value)}
                  placeholder="House, Road, Area..."
                />
              </div>
            )}

            {/* Payment Method */}
            <div className="form-group">
              <label className="input-label">Payment Method</label>
              <select
                className="select-input"
                value={paymentMethod}
                onChange={(e) => setPaymentMethod(e.target.value as PaymentMethod)}
              >
                <option value="CASH">💵 Cash on Delivery / Pickup</option>
                <option value="CARD">💳 Credit / Debit Card</option>
                <option value="MOBILE_BANKING">📱 Mobile Banking (bKash/Nagad)</option>
              </select>
            </div>

            {/* Coupon Code */}
            <div className="form-group">
              <label className="input-label flex items-center gap-1">
                <Tag size={14} className="text-amber" /> Coupon Code
              </label>
              <div className="flex gap-2">
                <input
                  type="text"
                  className="text-input uppercase"
                  value={couponCode}
                  onChange={(e) => setCouponCode(e.target.value)}
                  placeholder="e.g. WELCOME10 or FAMILY15"
                />
              </div>
              <span className="input-hint">
                Codes: <b>WELCOME10</b> (10% off), <b>FAMILY15</b> (15% off orders &ge; ৳1000)
              </span>
            </div>

            {/* Loyalty Points */}
            <div className="form-group">
              <label className="input-label flex justify-between">
                <span>Loyalty Points to Redeem</span>
                <span className="text-amber font-semibold">{loyaltyPoints} pts (-৳{loyaltyDiscount})</span>
              </label>
              <input
                type="range"
                min="0"
                max="100"
                value={loyaltyPoints}
                onChange={(e) => setLoyaltyPoints(parseInt(e.target.value) || 0)}
                className="w-full range-input"
              />
            </div>

            {/* Toggles */}
            <div className="grid grid-cols-2 gap-2 my-4">
              <label className={`toggle-card compact ${giftWrap ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={giftWrap}
                  onChange={(e) => setGiftWrap(e.target.checked)}
                />
                <span className="text-xs font-medium flex items-center gap-1">
                  <Gift size={14} /> Gift Wrap (+৳{CONSTANTS.GIFT_WRAP_FEE})
                </span>
              </label>

              <label className={`toggle-card compact ${rushOrder ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={rushOrder}
                  onChange={(e) => setRushOrder(e.target.checked)}
                />
                <span className="text-xs font-medium flex items-center gap-1">
                  <Zap size={14} /> Rush Order (+৳{CONSTANTS.RUSH_FEE})
                </span>
              </label>
            </div>

            <label className={`toggle-card compact mb-4 ${cutleryRequired ? 'checked' : ''}`}>
              <input
                type="checkbox"
                checked={cutleryRequired}
                onChange={(e) => setCutleryRequired(e.target.checked)}
              />
              <span className="text-xs font-medium">Include Cutlery & Napkins</span>
            </label>

            {/* Scheduled Time */}
            <div className="form-group">
              <label className="input-label flex items-center gap-1">
                <Clock size={14} /> Scheduled Delivery Time (Optional)
              </label>
              <input
                type="datetime-local"
                className="text-input"
                value={scheduledTime}
                onChange={(e) => setScheduledTime(e.target.value)}
              />
            </div>

            {/* Special Instructions */}
            <div className="form-group">
              <label className="input-label">Special Instructions</label>
              <input
                type="text"
                className="text-input"
                value={specialInstructions}
                onChange={(e) => setSpecialInstructions(e.target.value)}
                placeholder="e.g. Call before delivery, ring bell twice"
              />
            </div>

            {/* Pricing Summary */}
            <div className="price-breakdown-card">
              <div className="summary-row">
                <span>Subtotal</span>
                <span>৳{subtotal.toFixed(2)}</span>
              </div>
              <div className="summary-row">
                <span>Service Charges</span>
                <span>+৳{serviceCharges.toFixed(2)}</span>
              </div>
              {totalDiscount > 0 && (
                <div className="summary-row text-emerald-400">
                  <span>Discounts</span>
                  <span>-৳{totalDiscount.toFixed(2)}</span>
                </div>
              )}
              <div className="summary-row total-row">
                <span>Total Amount</span>
                <span className="text-amber">৳{total.toFixed(2)}</span>
              </div>
            </div>

            <button className="btn-primary w-full mt-4 text-base py-3" onClick={handleCheckout}>
              <ShieldCheck size={18} />
              Construct & Place Order
            </button>
          </div>
        </div>
      )}
    </div>
  );
};
