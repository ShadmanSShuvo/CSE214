import React, { useState } from 'react';
import type { MenuItem, Category } from '../types';
import { Search, SlidersHorizontal } from 'lucide-react';

interface MenuCatalogProps {
  items: MenuItem[];
  onSelectItem: (item: MenuItem) => void;
}

const CATEGORIES: Category[] = ['All', 'Burger', 'Pizza', 'Drink', 'Sides'];

export const MenuCatalog: React.FC<MenuCatalogProps> = ({ items, onSelectItem }) => {
  const [selectedCategory, setSelectedCategory] = useState<Category>('All');
  const [searchQuery, setSearchQuery] = useState<string>('');

  const filteredItems = items.filter((item) => {
    const matchesCategory = selectedCategory === 'All' || item.category === selectedCategory;
    const matchesSearch =
      item.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.code.toLowerCase().includes(searchQuery.toLowerCase()) ||
      (item.description && item.description.toLowerCase().includes(searchQuery.toLowerCase()));
    return matchesCategory && matchesSearch;
  });

  return (
    <div className="menu-container">
      <div className="menu-header">
        <div>
          <h2 className="section-title">Gourmet Selection</h2>
          <p className="section-subtitle">
            Configure food items using the OrderItem.Builder pattern with customizable options.
          </p>
        </div>

        <div className="search-bar">
          <Search size={18} className="search-icon" />
          <input
            type="text"
            placeholder="Search burgers, pizzas, drinks..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="search-input"
          />
        </div>
      </div>

      <div className="category-pills">
        {CATEGORIES.map((cat) => (
          <button
            key={cat}
            className={`pill-btn ${selectedCategory === cat ? 'active' : ''}`}
            onClick={() => setSelectedCategory(cat)}
          >
            {cat}
          </button>
        ))}
      </div>

      {filteredItems.length === 0 ? (
        <div className="empty-state">
          <p>No food items match your filter.</p>
        </div>
      ) : (
        <div className="menu-grid">
          {filteredItems.map((item) => (
            <div key={item.code} className="food-card">
              <div className="food-image-wrapper">
                <img
                  src={item.image || 'https://images.unsplash.com/photo-1546069901-ba9599a7e63c?auto=format&fit=crop&w=600&q=80'}
                  alt={item.name}
                  className="food-image"
                />
                <span className="code-badge">{item.code}</span>
                <span className="category-badge">{item.category}</span>
              </div>

              <div className="food-card-body">
                <div className="food-card-header">
                  <h3 className="food-title">{item.name}</h3>
                  <span className="food-price">৳{item.basePrice.toFixed(0)}</span>
                </div>
                <p className="food-description">{item.description}</p>

                <div className="food-card-actions">
                  <button className="btn-primary w-full" onClick={() => onSelectItem(item)}>
                    <SlidersHorizontal size={16} />
                    Customize & Add
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
