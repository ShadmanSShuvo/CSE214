import React, { useState, useEffect } from 'react';
import type { MenuItem, Size, OrderItem } from '../types';
import { OrderItemBuilder, CONSTANTS, calculateUnitPrice } from '../services/foodFlowEngine';
import { X, Plus, Minus, Check, Flame } from 'lucide-react';

interface ItemCustomizeModalProps {
  item: MenuItem | null;
  onClose: () => void;
  onAddToCart: (orderItem: OrderItem) => void;
}

export const ItemCustomizeModal: React.FC<ItemCustomizeModalProps> = ({ item, onClose, onAddToCart }) => {
  if (!item) return null;

  const [quantity, setQuantity] = useState<number>(1);
  const [size, setSize] = useState<Size>('MEDIUM');
  const [extraCheese, setExtraCheese] = useState<boolean>(false);
  const [spicy, setSpicy] = useState<boolean>(false);
  const [note, setNote] = useState<string>('');

  useEffect(() => {
    setQuantity(1);
    setSize('MEDIUM');
    setExtraCheese(false);
    setSpicy(false);
    setNote('');
  }, [item]);

  const unitPrice = calculateUnitPrice(item, size, extraCheese);
  const subtotal = unitPrice * quantity;

  const handleAdd = () => {
    const orderItem = new OrderItemBuilder(item, quantity)
      .setSize(size)
      .setExtraCheese(extraCheese)
      .setSpicy(spicy)
      .setNote(note)
      .build();

    onAddToCart(orderItem);
    onClose();
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-content text-left" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>
          <X size={20} />
        </button>

        <div className="customize-header">
          <img
            src={item.image || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=600&q=80'}
            alt={item.name}
            className="customize-hero-img"
          />
          <div className="customize-info">
            <span className="code-badge">{item.code}</span>
            <h2 className="modal-title">{item.name}</h2>
            <p className="food-description">{item.description}</p>
            <div className="base-price-tag">Base Price: ৳{item.basePrice}</div>
          </div>
        </div>

        <div className="customize-body">
          {/* Size Selection */}
          <div className="option-group">
            <label className="option-label">Portion Size</label>
            <div className="size-selector">
              {(['SMALL', 'MEDIUM', 'LARGE'] as Size[]).map((s) => {
                const mult = s === 'SMALL' ? 0.8 : s === 'LARGE' ? 1.25 : 1.0;
                const calcPrice = item.basePrice * mult;
                return (
                  <button
                    key={s}
                    className={`size-btn ${size === s ? 'selected' : ''}`}
                    onClick={() => setSize(s)}
                  >
                    <span className="size-name">{s}</span>
                    <span className="size-price">৳{calcPrice.toFixed(0)}</span>
                  </button>
                );
              })}
            </div>
          </div>

          {/* Add-ons & Toggles */}
          <div className="option-group">
            <label className="option-label">Add-ons & Flavoring</label>
            <div className="toggle-list">
              <label className={`toggle-card ${extraCheese ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={extraCheese}
                  onChange={(e) => setExtraCheese(e.target.checked)}
                />
                <div className="toggle-content">
                  <div>
                    <span className="font-medium">Extra Cheese</span>
                    <span className="text-xs text-muted block">Rich melted slice (+৳{CONSTANTS.EXTRA_CHEESE_PRICE})</span>
                  </div>
                  {extraCheese && <Check size={18} className="text-amber" />}
                </div>
              </label>

              <label className={`toggle-card ${spicy ? 'checked' : ''}`}>
                <input
                  type="checkbox"
                  checked={spicy}
                  onChange={(e) => setSpicy(e.target.checked)}
                />
                <div className="toggle-content">
                  <div>
                    <span className="font-medium flex items-center gap-1">
                      Spicy Level <Flame size={14} className="text-red-500 fill-red-500" />
                    </span>
                    <span className="text-xs text-muted block">Add chili flakes & spicy dip sauce</span>
                  </div>
                  {spicy && <Check size={18} className="text-amber" />}
                </div>
              </label>
            </div>
          </div>

          {/* Special Note */}
          <div className="option-group">
            <label className="option-label">Special Instructions for Kitchen</label>
            <input
              type="text"
              placeholder="e.g. half spicy, less sugar, sauce on side"
              value={note}
              onChange={(e) => setNote(e.target.value)}
              className="text-input"
            />
          </div>

          {/* Quantity Stepper */}
          <div className="quantity-row">
            <span className="option-label m-0">Quantity</span>
            <div className="stepper">
              <button
                className="stepper-btn"
                onClick={() => setQuantity((q) => Math.max(1, q - 1))}
              >
                <Minus size={16} />
              </button>
              <span className="stepper-value">{quantity}</span>
              <button
                className="stepper-btn"
                onClick={() => setQuantity((q) => q + 1)}
              >
                <Plus size={16} />
              </button>
            </div>
          </div>
        </div>

        <div className="modal-footer">
          <div className="price-summary">
            <span className="text-muted text-xs uppercase tracking-wider">Subtotal</span>
            <span className="text-xl font-bold text-amber">৳{subtotal.toFixed(2)}</span>
          </div>

          <button className="btn-primary" onClick={handleAdd}>
            Add to Order
          </button>
        </div>
      </div>
    </div>
  );
};
