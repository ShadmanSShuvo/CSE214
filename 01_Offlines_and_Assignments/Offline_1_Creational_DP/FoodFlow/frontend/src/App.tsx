import { useState } from 'react';
import confetti from 'canvas-confetti';
import type { MenuItem, OrderItem, Order } from './types';
import { DEFAULT_MENU } from './data/defaultMenu';
import { OrderItemBuilder, OrderBuilder } from './services/foodFlowEngine';
import { Navbar } from './components/Navbar';
import { MenuCatalog } from './components/MenuCatalog';
import { ItemCustomizeModal } from './components/ItemCustomizeModal';
import { CartDrawer } from './components/CartDrawer';
import { ReceiptModal } from './components/ReceiptModal';
import { PatternInspector } from './components/PatternInspector';
import { OrderHistoryModal } from './components/OrderHistoryModal';
import { AddMenuItemModal } from './components/AddMenuItemModal';

export function App() {
  const [activeTab, setActiveTab] = useState<'menu' | 'cart' | 'inspector' | 'history'>('menu');
  const [menuItems, setMenuItems] = useState<MenuItem[]>(DEFAULT_MENU);
  const [cartItems, setCartItems] = useState<OrderItem[]>([]);
  const [orderHistory, setOrderHistory] = useState<Order[]>([]);
  
  const [selectedMenuItem, setSelectedMenuItem] = useState<MenuItem | null>(null);
  const [activeReceiptOrder, setActiveReceiptOrder] = useState<Order | null>(null);
  const [isAddItemOpen, setIsAddItemOpen] = useState<boolean>(false);
  const [lastPlacedOrder, setLastPlacedOrder] = useState<Order | null>(null);

  // Cart Management
  const handleAddToCart = (item: OrderItem) => {
    setCartItems((prev) => [...prev, item]);
  };

  const handleUpdateQuantity = (id: string, newQty: number) => {
    if (newQty <= 0) {
      handleRemoveItem(id);
      return;
    }
    setCartItems((prev) =>
      prev.map((i) => (i.id === id ? { ...i, quantity: newQty, subtotal: i.unitPrice * newQty } : i))
    );
  };

  const handleRemoveItem = (id: string) => {
    setCartItems((prev) => prev.filter((i) => i.id !== id));
  };

  const handleClearCart = () => {
    setCartItems([]);
  };

  // Order Placement
  const handleOrderSuccess = (order: Order) => {
    setOrderHistory((prev) => [order, ...prev]);
    setLastPlacedOrder(order);
    setActiveReceiptOrder(order);
    setCartItems([]);
    confetti({
      particleCount: 80,
      spread: 70,
      origin: { y: 0.6 },
    });
  };

  // Presets matching OrderService.java
  const handleApplyFamilyPreset = () => {
    const p01 = menuItems.find((i) => i.code === 'P01') || menuItems[2];
    const b02 = menuItems.find((i) => i.code === 'B02') || menuItems[1];
    const d02 = menuItems.find((i) => i.code === 'D02') || menuItems[5];
    const s02 = menuItems.find((i) => i.code === 'S02') || menuItems[7];

    const presetItems: OrderItem[] = [
      new OrderItemBuilder(p01, 2).setSize('LARGE').setExtraCheese(true).setNote('half spicy').build(),
      new OrderItemBuilder(b02, 3).setSize('MEDIUM').setExtraCheese(true).setSpicy(true).build(),
      new OrderItemBuilder(d02, 4).setSize('MEDIUM').setNote('less sugar').build(),
      new OrderItemBuilder(s02, 2).setSize('LARGE').setSpicy(true).build(),
    ];

    const orderId = `FF-FAMILY-${Math.floor(100 + Math.random() * 900)}`;
    const familyOrder = new OrderBuilder(orderId, 'Sample Family', '01711111111', presetItems)
      .setDeliveryType('DELIVERY')
      .setDeliveryAddress('House 25, Road 4, Dhanmondi')
      .setPaymentMethod('MOBILE_BANKING')
      .setCouponCode('FAMILY15')
      .setLoyaltyPointsToRedeem(50)
      .setRushOrder(true)
      .setSpecialInstructions('Deliver together')
      .build();

    handleOrderSuccess(familyOrder);
  };

  const handleApplyScheduledGiftPreset = () => {
    const p02 = menuItems.find((i) => i.code === 'P02') || menuItems[3];
    const d01 = menuItems.find((i) => i.code === 'D01') || menuItems[4];

    const presetItems: OrderItem[] = [
      new OrderItemBuilder(p02, 1).setSize('MEDIUM').build(),
      new OrderItemBuilder(d01, 2).setSize('SMALL').build(),
    ];

    const orderId = `FF-GIFT-${Math.floor(100 + Math.random() * 900)}`;
    const giftOrder = new OrderBuilder(orderId, 'Alice', '01700000000', presetItems)
      .setDeliveryType('DELIVERY')
      .setDeliveryAddress('House 12, Road 7, Dhanmondi')
      .setPaymentMethod('CARD')
      .setScheduledTime('2026-08-06T18:00')
      .setCouponCode('WELCOME10')
      .setGiftWrap(true)
      .setCutleryRequired(false)
      .setLoyaltyPointsToRedeem(25)
      .setSpecialInstructions('Please call before delivery')
      .build();

    handleOrderSuccess(giftOrder);
  };

  const handleAddCustomMenuItem = (item: MenuItem) => {
    setMenuItems((prev) => [item, ...prev]);
  };

  return (
    <div className="app-container">
      <Navbar
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        cartCount={cartItems.length}
        onOpenAddItem={() => setIsAddItemOpen(true)}
        onApplyFamilyPreset={handleApplyFamilyPreset}
        onApplyScheduledGiftPreset={handleApplyScheduledGiftPreset}
      />

      <main className="main-content">
        {activeTab === 'menu' && (
          <MenuCatalog
            items={menuItems}
            onSelectItem={(item) => setSelectedMenuItem(item)}
          />
        )}

        {activeTab === 'cart' && (
          <CartDrawer
            items={cartItems}
            onUpdateQuantity={handleUpdateQuantity}
            onRemoveItem={handleRemoveItem}
            onClearCart={handleClearCart}
            onOrderSuccess={handleOrderSuccess}
          />
        )}

        {activeTab === 'inspector' && (
          <PatternInspector lastOrder={lastPlacedOrder} />
        )}

        {activeTab === 'history' && (
          <OrderHistoryModal
            orders={orderHistory}
            onSelectOrder={(order) => setActiveReceiptOrder(order)}
          />
        )}
      </main>

      {/* Item Customization Modal */}
      <ItemCustomizeModal
        item={selectedMenuItem}
        onClose={() => setSelectedMenuItem(null)}
        onAddToCart={handleAddToCart}
      />

      {/* Order Receipt Modal */}
      <ReceiptModal
        order={activeReceiptOrder}
        onClose={() => setActiveReceiptOrder(null)}
        onViewPattern={() => {
          setActiveReceiptOrder(null);
          setActiveTab('inspector');
        }}
      />

      {/* Add New Menu Item Modal */}
      <AddMenuItemModal
        isOpen={isAddItemOpen}
        onClose={() => setIsAddItemOpen(false)}
        onAddItem={handleAddCustomMenuItem}
      />
    </div>
  );
}

export default App;
