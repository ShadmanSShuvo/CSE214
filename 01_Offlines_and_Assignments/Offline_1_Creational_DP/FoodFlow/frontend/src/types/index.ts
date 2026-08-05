export type Category = 'All' | 'Burger' | 'Pizza' | 'Drink' | 'Sides';

export type Size = 'SMALL' | 'MEDIUM' | 'LARGE';

export type DeliveryType = 'PICKUP' | 'DELIVERY';

export type PaymentMethod = 'CASH' | 'CARD' | 'MOBILE_BANKING';

export interface MenuItem {
  code: string;
  name: string;
  category: Category;
  basePrice: number;
  description?: string;
  image?: string;
}

export interface OrderItem {
  id: string;
  menuItem: MenuItem;
  quantity: number;
  size: Size;
  extraCheese: boolean;
  spicy: boolean;
  note: string;
  unitPrice: number;
  subtotal: number;
}

export interface Order {
  orderId: string;
  customerName: string;
  phone: string;
  deliveryType: DeliveryType;
  deliveryAddress: string;
  paymentMethod: PaymentMethod;
  scheduledTime: string | null;
  couponCode: string;
  giftWrap: boolean;
  cutleryRequired: boolean;
  loyaltyPointsToRedeem: number;
  rushOrder: boolean;
  items: OrderItem[];
  specialInstructions: string;
  subtotal: number;
  discount: number;
  serviceCharges: number;
  total: number;
  createdAt: string;
}

export interface PresetOrder {
  title: string;
  description: string;
  badge: string;
  iconName: string;
  apply: () => void;
}
