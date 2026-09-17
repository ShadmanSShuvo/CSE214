import type { MenuItem, OrderItem, Order, Size, DeliveryType, PaymentMethod } from '../types';

export const CONSTANTS = {
  DELIVERY_FEE: 80.0,
  RUSH_FEE: 120.0,
  GIFT_WRAP_FEE: 50.0,
  EXTRA_CHEESE_PRICE: 60.0,
};

export const SIZE_MULTIPLIERS: Record<Size, number> = {
  SMALL: 0.8,
  MEDIUM: 1.0,
  LARGE: 1.25,
};

export class OrderItemBuilder {
  private menuItem: MenuItem;
  private quantity: number;
  private size: Size = 'MEDIUM';
  private extraCheese: boolean = false;
  private spicy: boolean = false;
  private note: string = '';

  constructor(menuItem: MenuItem, quantity: number) {
    this.menuItem = menuItem;
    this.quantity = quantity;
  }

  setSize(size: Size): OrderItemBuilder {
    this.size = size;
    return this;
  }

  setExtraCheese(extraCheese: boolean): OrderItemBuilder {
    this.extraCheese = extraCheese;
    return this;
  }

  setSpicy(spicy: boolean): OrderItemBuilder {
    this.spicy = spicy;
    return this;
  }

  setNote(note: string): OrderItemBuilder {
    this.note = note;
    return this;
  }

  build(): OrderItem {
    if (!this.menuItem) {
      throw new Error('Menu item cannot be null');
    }
    if (this.quantity <= 0) {
      throw new Error('Quantity must be positive');
    }

    const unitPrice = calculateUnitPrice(this.menuItem, this.size, this.extraCheese);
    const subtotal = unitPrice * this.quantity;

    return {
      id: Math.random().toString(36).substring(2, 9),
      menuItem: this.menuItem,
      quantity: this.quantity,
      size: this.size,
      extraCheese: this.extraCheese,
      spicy: this.spicy,
      note: this.note ? this.note.trim() : '',
      unitPrice,
      subtotal,
    };
  }
}

export function calculateUnitPrice(menuItem: MenuItem, size: Size, extraCheese: boolean): number {
  const multiplier = SIZE_MULTIPLIERS[size] || 1.0;
  let price = menuItem.basePrice * multiplier;
  if (extraCheese) {
    price += CONSTANTS.EXTRA_CHEESE_PRICE;
  }
  return price;
}

export function describeOrderItemOptions(item: OrderItem): string {
  const parts: string[] = [item.size];
  if (item.extraCheese) parts.push('extra cheese');
  if (item.spicy) parts.push('spicy');
  if (item.note) parts.push(`note: ${item.note}`);
  return parts.join(', ');
}

export class OrderBuilder {
  private orderId: string;
  private customerName: string;
  private phone: string;
  private items: OrderItem[];

  private deliveryType: DeliveryType = 'PICKUP';
  private deliveryAddress: string = '';
  private paymentMethod: PaymentMethod = 'CASH';
  private scheduledTime: string | null = null;
  private couponCode: string = '';
  private giftWrap: boolean = false;
  private cutleryRequired: boolean = true;
  private loyaltyPointsToRedeem: number = 0;
  private rushOrder: boolean = false;
  private specialInstructions: string = '';

  constructor(orderId: string, customerName: string, phone: string, items: OrderItem[]) {
    this.orderId = orderId;
    this.customerName = customerName;
    this.phone = phone;
    this.items = items;
  }

  setDeliveryType(type: DeliveryType): OrderBuilder {
    this.deliveryType = type;
    return this;
  }

  setDeliveryAddress(address: string): OrderBuilder {
    this.deliveryAddress = address;
    return this;
  }

  setPaymentMethod(method: PaymentMethod): OrderBuilder {
    this.paymentMethod = method;
    return this;
  }

  setScheduledTime(time: string | null): OrderBuilder {
    this.scheduledTime = time;
    return this;
  }

  setCouponCode(code: string): OrderBuilder {
    this.couponCode = code;
    return this;
  }

  setGiftWrap(wrap: boolean): OrderBuilder {
    this.giftWrap = wrap;
    return this;
  }

  setCutleryRequired(required: boolean): OrderBuilder {
    this.cutleryRequired = required;
    return this;
  }

  setLoyaltyPointsToRedeem(points: number): OrderBuilder {
    this.loyaltyPointsToRedeem = points;
    return this;
  }

  setRushOrder(rush: boolean): OrderBuilder {
    this.rushOrder = rush;
    return this;
  }

  setSpecialInstructions(instructions: string): OrderBuilder {
    this.specialInstructions = instructions;
    return this;
  }

  build(): Order {
    if (!this.orderId || !this.orderId.trim()) {
      throw new Error('Order id cannot be blank');
    }
    if (!this.customerName || !this.customerName.trim()) {
      throw new Error('Customer name cannot be blank');
    }
    if (!this.phone || !this.phone.trim()) {
      throw new Error('Phone cannot be blank');
    }
    if (!this.items || this.items.length === 0) {
      throw new Error('Order must contain at least one item');
    }

    const resolvedDeliveryType = this.deliveryType || 'PICKUP';
    let resolvedDeliveryAddress = '';

    if (resolvedDeliveryType === 'DELIVERY') {
      if (!this.deliveryAddress || !this.deliveryAddress.trim()) {
        throw new Error('Delivery address cannot be blank for delivery orders');
      }
      resolvedDeliveryAddress = this.deliveryAddress.trim();
    } else {
      resolvedDeliveryAddress = this.deliveryAddress ? this.deliveryAddress.trim() : '';
    }

    const normalizedCoupon = (this.couponCode || '').trim().toUpperCase();
    const clampedLoyalty = Math.max(0, this.loyaltyPointsToRedeem);

    const subtotal = this.items.reduce((sum, item) => sum + item.subtotal, 0);

    let couponDiscount = 0.0;
    if (normalizedCoupon === 'WELCOME10') {
      couponDiscount = subtotal * 0.10;
    } else if (normalizedCoupon === 'FAMILY15' && subtotal >= 1000.0) {
      couponDiscount = subtotal * 0.15;
    }

    const loyaltyDiscount = Math.min(clampedLoyalty, 100);
    const discount = couponDiscount + loyaltyDiscount;

    let serviceCharges = 0.0;
    if (resolvedDeliveryType === 'DELIVERY') {
      serviceCharges += CONSTANTS.DELIVERY_FEE;
    }
    if (this.rushOrder) {
      serviceCharges += CONSTANTS.RUSH_FEE;
    }
    if (this.giftWrap) {
      serviceCharges += CONSTANTS.GIFT_WRAP_FEE;
    }

    const total = Math.max(0.0, subtotal + serviceCharges - discount);

    return {
      orderId: this.orderId.trim(),
      customerName: this.customerName.trim(),
      phone: this.phone.trim(),
      deliveryType: resolvedDeliveryType,
      deliveryAddress: resolvedDeliveryAddress,
      paymentMethod: this.paymentMethod || 'CASH',
      scheduledTime: this.scheduledTime,
      couponCode: normalizedCoupon,
      giftWrap: this.giftWrap,
      cutleryRequired: this.cutleryRequired,
      loyaltyPointsToRedeem: clampedLoyalty,
      rushOrder: this.rushOrder,
      items: [...this.items],
      specialInstructions: (this.specialInstructions || '').trim(),
      subtotal,
      discount,
      serviceCharges,
      total,
      createdAt: new Date().toISOString(),
    };
  }
}

export function formatReceipt(order: Order): string {
  const lineSeparator = '='.repeat(72);
  let receipt = 'FOODFLOW RECEIPT\n';
  receipt += `${lineSeparator}\n`;
  receipt += `Order ID: ${order.orderId}\n`;
  receipt += `Customer: ${order.customerName} (${order.phone})\n`;
  receipt += `Type: ${order.deliveryType}\n`;
  if (order.deliveryAddress) {
    receipt += `Address: ${order.deliveryAddress}\n`;
  }
  if (order.scheduledTime) {
    receipt += `Scheduled: ${order.scheduledTime}\n`;
  }
  receipt += `Payment: ${order.paymentMethod}\n`;
  receipt += `${lineSeparator}\n`;

  for (const item of order.items) {
    const itemDesc = `${item.quantity}x ${item.menuItem.name.padEnd(20)} ${describeOrderItemOptions(item).padEnd(32)}`;
    const priceStr = item.subtotal.toFixed(2).padStart(8);
    receipt += `${itemDesc.padEnd(60)} ${priceStr}\n`;
  }

  receipt += `${lineSeparator}\n`;
  receipt += `${'Subtotal:'.padEnd(25)} ${order.subtotal.toFixed(2).padStart(10)}\n`;
  receipt += `${'Service charges:'.padEnd(25)} ${order.serviceCharges.toFixed(2).padStart(10)}\n`;
  receipt += `${'Discount:'.padEnd(25)} ${order.discount.toFixed(2).padStart(10)}\n`;
  receipt += `${'Total:'.padEnd(25)} ${order.total.toFixed(2).padStart(10)}\n`;

  if (order.specialInstructions) {
    receipt += `Instructions: ${order.specialInstructions}\n`;
  }

  return receipt;
}

export function generateJavaCode(order: Order): string {
  let java = `// Created using FoodFlow Order.Builder pattern\n`;
  java += `Order order = Order.builder("${order.orderId}", "${order.customerName}", "${order.phone}", items)\n`;
  if (order.deliveryType !== 'PICKUP') {
    java += `    .deliveryType(DeliveryType.${order.deliveryType})\n`;
  }
  if (order.deliveryAddress) {
    java += `    .deliveryAddress("${order.deliveryAddress}")\n`;
  }
  if (order.paymentMethod !== 'CASH') {
    java += `    .paymentMethod(PaymentMethod.${order.paymentMethod})\n`;
  }
  if (order.scheduledTime) {
    java += `    .scheduledTime(LocalDateTime.parse("${order.scheduledTime}"))\n`;
  }
  if (order.couponCode) {
    java += `    .couponCode("${order.couponCode}")\n`;
  }
  if (order.giftWrap) {
    java += `    .giftWrap(true)\n`;
  }
  if (!order.cutleryRequired) {
    java += `    .cutleryRequired(false)\n`;
  }
  if (order.loyaltyPointsToRedeem > 0) {
    java += `    .loyaltyPointsToRedeem(${order.loyaltyPointsToRedeem})\n`;
  }
  if (order.rushOrder) {
    java += `    .rushOrder(true)\n`;
  }
  if (order.specialInstructions) {
    java += `    .specialInstructions("${order.specialInstructions}")\n`;
  }
  java += `    .build();`;
  return java;
}
