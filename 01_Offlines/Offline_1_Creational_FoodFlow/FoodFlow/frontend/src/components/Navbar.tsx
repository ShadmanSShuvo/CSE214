import React from 'react';
import { ShoppingBag, Cpu, History, PlusCircle, Sparkles, UtensilsCrossed } from 'lucide-react';

interface NavbarProps {
  activeTab: 'menu' | 'cart' | 'inspector' | 'history';
  setActiveTab: (tab: 'menu' | 'cart' | 'inspector' | 'history') => void;
  cartCount: number;
  onOpenAddItem: () => void;
  onApplyFamilyPreset: () => void;
  onApplyScheduledGiftPreset: () => void;
}

export const Navbar: React.FC<NavbarProps> = ({
  activeTab,
  setActiveTab,
  cartCount,
  onOpenAddItem,
  onApplyFamilyPreset,
  onApplyScheduledGiftPreset,
}) => {
  return (
    <header className="navbar-container">
      <div className="navbar-inner">
        <div className="navbar-brand" onClick={() => setActiveTab('menu')}>
          <div className="brand-logo">
            <UtensilsCrossed size={24} className="text-amber" />
          </div>
          <div>
            <h1 className="brand-title">FoodFlow</h1>
            <span className="brand-subtitle">Creational DP Architecture</span>
          </div>
        </div>

        <nav className="navbar-links">
          <button
            className={`nav-tab ${activeTab === 'menu' ? 'active' : ''}`}
            onClick={() => setActiveTab('menu')}
          >
            Menu Catalog
          </button>
          <button
            className={`nav-tab ${activeTab === 'cart' ? 'active' : ''}`}
            onClick={() => setActiveTab('cart')}
          >
            <ShoppingBag size={18} />
            Cart & Checkout
            {cartCount > 0 && <span className="cart-badge">{cartCount}</span>}
          </button>
          <button
            className={`nav-tab ${activeTab === 'inspector' ? 'active' : ''}`}
            onClick={() => setActiveTab('inspector')}
          >
            <Cpu size={18} />
            Builder DP Visualizer
          </button>
          <button
            className={`nav-tab ${activeTab === 'history' ? 'active' : ''}`}
            onClick={() => setActiveTab('history')}
          >
            <History size={18} />
            Orders
          </button>
        </nav>

        <div className="navbar-actions">
          <div className="dropdown-preset">
            <button className="btn-preset">
              <Sparkles size={16} />
              Quick Presets
            </button>
            <div className="dropdown-menu">
              <button onClick={onApplyFamilyPreset} className="dropdown-item">
                👨‍👩‍👧‍👦 Sample Family Order
              </button>
              <button onClick={onApplyScheduledGiftPreset} className="dropdown-item">
                🎁 Scheduled Gift Order
              </button>
            </div>
          </div>

          <button className="btn-secondary" onClick={onOpenAddItem}>
            <PlusCircle size={16} />
            Add Item
          </button>
        </div>
      </div>
    </header>
  );
};
