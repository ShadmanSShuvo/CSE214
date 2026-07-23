package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Order {
    public static final double DELIVERY_FEE = 80.0;
    public static final double RUSH_FEE = 120.0;
    public static final double GIFT_WRAP_FEE = 50.0;

    private final String orderId;
    private final String customerName;
    private final String phone;
    private final DeliveryType deliveryType;
    private final String deliveryAddress;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime scheduledTime;
    private final String couponCode;
    private final boolean giftWrap;
    private final boolean cutleryRequired;
    private final int loyaltyPointsToRedeem;
    private final boolean rushOrder;
    private final List<OrderItem> items;
    private final String specialInstructions;

    private Order(String orderId,
            String customerName,
            String phone,
            DeliveryType deliveryType,
            String deliveryAddress,
            PaymentMethod paymentMethod,
            LocalDateTime scheduledTime,
            String couponCode,
            boolean giftWrap,
            boolean cutleryRequired,
            int loyaltyPointsToRedeem,
            boolean rushOrder,
            List<OrderItem> items,
            String specialInstructions) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phone = phone;
        this.deliveryType = deliveryType;
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.scheduledTime = scheduledTime;
        this.couponCode = couponCode;
        this.giftWrap = giftWrap;
        this.cutleryRequired = cutleryRequired;
        this.loyaltyPointsToRedeem = loyaltyPointsToRedeem;
        this.rushOrder = rushOrder;
        this.items = items;
        this.specialInstructions = specialInstructions;
    }


    public static Builder builder(String orderId, String customerName, String phone, List<OrderItem> items) {
        return new Builder(orderId, customerName, phone, items);
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhone() {
        return phone;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public boolean isGiftWrap() {
        return giftWrap;
    }

    public boolean isCutleryRequired() {
        return cutleryRequired;
    }

    public int getLoyaltyPointsToRedeem() {
        return loyaltyPointsToRedeem;
    }

    public boolean isRushOrder() {
        return rushOrder;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public double getDiscount() {
        double couponDiscount = 0.0;
        if ("WELCOME10".equals(couponCode)) {
            couponDiscount = getSubtotal() * 0.10;
        } else if ("FAMILY15".equals(couponCode) && getSubtotal() >= 1000.0) {
            couponDiscount = getSubtotal() * 0.15;
        }

        double loyaltyDiscount = Math.min(loyaltyPointsToRedeem, 100);
        return couponDiscount + loyaltyDiscount;
    }

    public double getServiceCharges() {
        double charges = 0.0;
        if (deliveryType == DeliveryType.DELIVERY) {
            charges += DELIVERY_FEE;
        }
        if (rushOrder) {
            charges += RUSH_FEE;
        }
        if (giftWrap) {
            charges += GIFT_WRAP_FEE;
        }
        return charges;
    }

    public double getTotal() {
        return Math.max(0.0, getSubtotal() + getServiceCharges() - getDiscount());
    }

    private static String requireNonBlank(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " cannot be null");
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return trimmed;
    }

    
    public static class Builder {
        private final String orderId;
        private final String customerName;
        private final String phone;
        private final List<OrderItem> items;

        private DeliveryType deliveryType = DeliveryType.PICKUP;
        private String deliveryAddress = "";
        private PaymentMethod paymentMethod = PaymentMethod.CASH;
        private LocalDateTime scheduledTime = null;
        private String couponCode = "";
        private boolean giftWrap = false;
        private boolean cutleryRequired = true;
        private int loyaltyPointsToRedeem = 0;
        private boolean rushOrder = false;
        private String specialInstructions = "";

        private Builder(String orderId, String customerName, String phone, List<OrderItem> items) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.phone = phone;
            this.items = items;
        }

        public Builder deliveryType(DeliveryType deliveryType) {
            this.deliveryType = deliveryType;
            return this;
        }

        public Builder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder paymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder scheduledTime(LocalDateTime scheduledTime) {
            this.scheduledTime = scheduledTime;
            return this;
        }

        public Builder couponCode(String couponCode) {
            this.couponCode = couponCode;
            return this;
        }

        public Builder giftWrap(boolean giftWrap) {
            this.giftWrap = giftWrap;
            return this;
        }

        public Builder cutleryRequired(boolean cutleryRequired) {
            this.cutleryRequired = cutleryRequired;
            return this;
        }

        public Builder loyaltyPointsToRedeem(int loyaltyPointsToRedeem) {
            this.loyaltyPointsToRedeem = loyaltyPointsToRedeem;
            return this;
        }

        public Builder rushOrder(boolean rushOrder) {
            this.rushOrder = rushOrder;
            return this;
        }

        public Builder specialInstructions(String specialInstructions) {
            this.specialInstructions = specialInstructions;
            return this;
        }

        
        public Order build() {
            String validOrderId = requireNonBlank(orderId, "Order id");
            String validCustomerName = requireNonBlank(customerName, "Customer name");
            String validPhone = requireNonBlank(phone, "Phone");

            DeliveryType resolvedDeliveryType = deliveryType != null ? deliveryType : DeliveryType.PICKUP;
            PaymentMethod resolvedPaymentMethod = paymentMethod != null ? paymentMethod : PaymentMethod.CASH;
            String normalizedCouponCode = couponCode != null ? couponCode.trim().toUpperCase() : "";
            int clampedLoyaltyPoints = Math.max(0, loyaltyPointsToRedeem);
            String trimmedSpecialInstructions = specialInstructions != null ? specialInstructions.trim() : "";

            String resolvedDeliveryAddress;
            if (resolvedDeliveryType == DeliveryType.DELIVERY) {
                resolvedDeliveryAddress = requireNonBlank(deliveryAddress, "Delivery address");
            } else {
                resolvedDeliveryAddress = deliveryAddress != null ? deliveryAddress.trim() : "";
            }

            Objects.requireNonNull(items, "Items cannot be null");
            if (items.isEmpty()) {
                throw new IllegalArgumentException("Order must contain at least one item");
            }
            List<OrderItem> copiedItems = Collections.unmodifiableList(new ArrayList<>(items));

            return new Order(validOrderId, validCustomerName, validPhone, resolvedDeliveryType,
                    resolvedDeliveryAddress, resolvedPaymentMethod, scheduledTime, normalizedCouponCode,
                    giftWrap, cutleryRequired, clampedLoyaltyPoints, rushOrder, copiedItems,
                    trimmedSpecialInstructions);
        }
    }
}