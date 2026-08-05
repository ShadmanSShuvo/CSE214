import React, { useState } from 'react';
import type { MenuItem, Category } from '../types';
import { X, PlusCircle } from 'lucide-react';

interface AddMenuItemModalProps {
  isOpen: boolean;
  onClose: () => void;
  onAddItem: (item: MenuItem) => void;
}

export const AddMenuItemModal: React.FC<AddMenuItemModalProps> = ({ isOpen, onClose, onAddItem }) => {
  if (!isOpen) return null;

  const [code, setCode] = useState('');
  const [name, setName] = useState('');
  const [category, setCategory] = useState<Category>('Burger');
  const [basePrice, setBasePrice] = useState<number>(300);
  const [description, setDescription] = useState('');
  const [image, setImage] = useState('');

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!code.trim() || !name.trim()) return;

    const newItem: MenuItem = {
      code: code.trim().toUpperCase(),
      name: name.trim(),
      category,
      basePrice: Number(basePrice) || 100,
      description: description.trim() || 'Delicious house specialty prepared fresh to order.',
      image: image.trim() || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=600&q=80',
    };

    onAddItem(newItem);
    onClose();
  };

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-content text-left max-w-md" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>
          <X size={20} />
        </button>

        <h2 className="modal-title flex items-center gap-2">
          <PlusCircle size={20} className="text-amber" /> Add New Menu Item
        </h2>
        <p className="text-xs text-muted mb-4">Add a new dish to the food catalog.</p>

        <form onSubmit={handleSubmit} className="space-y-3">
          <div>
            <label className="input-label">Item Code *</label>
            <input
              type="text"
              required
              className="text-input uppercase"
              placeholder="e.g. B03 or P03"
              value={code}
              onChange={(e) => setCode(e.target.value)}
            />
          </div>

          <div>
            <label className="input-label">Item Name *</label>
            <input
              type="text"
              required
              className="text-input"
              placeholder="e.g. Supreme BBQ Burger"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <div className="grid grid-cols-2 gap-2">
            <div>
              <label className="input-label">Category</label>
              <select
                className="select-input"
                value={category}
                onChange={(e) => setCategory(e.target.value as Category)}
              >
                <option value="Burger">Burger</option>
                <option value="Pizza">Pizza</option>
                <option value="Drink">Drink</option>
                <option value="Sides">Sides</option>
              </select>
            </div>

            <div>
              <label className="input-label">Base Price (৳) *</label>
              <input
                type="number"
                required
                min="1"
                className="text-input"
                value={basePrice}
                onChange={(e) => setBasePrice(parseFloat(e.target.value) || 0)}
              />
            </div>
          </div>

          <div>
            <label className="input-label">Description</label>
            <textarea
              className="text-input rows-2"
              placeholder="Brief ingredients or flavor description..."
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div>
            <label className="input-label">Image URL (Optional)</label>
            <input
              type="url"
              className="text-input"
              placeholder="https://..."
              value={image}
              onChange={(e) => setImage(e.target.value)}
            />
          </div>

          <div className="modal-footer pt-3">
            <button type="button" className="btn-secondary" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn-primary">
              Save Item
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};
