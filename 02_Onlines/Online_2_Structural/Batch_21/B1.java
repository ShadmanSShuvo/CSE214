interface Purchase {
    double calculatePrice();
}

// Concrete component: base purchase, no discount
class BasePurchase implements Purchase {
    private double price;

    public BasePurchase(double price) {
        this.price = price;
    }

    @Override
    public double calculatePrice() {
        return price;
    }
}

// Abstract decorator
abstract class DiscountDecorator implements Purchase {
    protected Purchase wrappedPurchase;

    public DiscountDecorator(Purchase purchase) {
        this.wrappedPurchase = purchase;
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice();
    }
}

// Concrete Decorator 1: Loyalty Discount (10% off)
class LoyaltyDiscount extends DiscountDecorator {
    public LoyaltyDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice() * 0.90;
    }
}

// Concrete Decorator 2: Seasonal Discount (flat 100 units off)
class SeasonalDiscount extends DiscountDecorator {
    public SeasonalDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        return wrappedPurchase.calculatePrice() - 100;
    }
}

// Concrete Decorator 3: High-Value Purchase Discount (2% off if > 10000)
class HighValueDiscount extends DiscountDecorator {
    public HighValueDiscount(Purchase purchase) {
        super(purchase);
    }

    @Override
    public double calculatePrice() {
        double price = wrappedPurchase.calculatePrice();
        return price;
    }
}
// cleaner version of `HighValueDiscount` (no anonymous class needed)
// class HighValueDiscount extends DiscountDecorator {
//     public HighValueDiscount(Purchase purchase) {
//         super(purchase);
//     }

//     @Override
//     public double calculatePrice() {
//         return wrappedPurchase.calculatePrice() * 0.98;
//     }
// }
// Then in`B1`,just do:
// if(basePrice>10000)
// {
//     purchase = new HighValueDiscount(purchase);
// }

public class B1 {
    public static void main(String[] args) {
        double basePrice = 12000; // Initial price of the product
        boolean isPremiumMember = true;
        boolean isSeasonalPromotion = true;

        // Create a base purchase
        Purchase purchase = new BasePurchase(basePrice);

        // Apply discounts conditionally
        if (isPremiumMember) {
            purchase = new LoyaltyDiscount(purchase);
        }
        if (isSeasonalPromotion) {
            purchase = new SeasonalDiscount(purchase);
        }
        // High-value check must be based on the ORIGINAL amount, per the spec
        if (basePrice > 10000) {
            purchase = new HighValueDiscount(purchase) {
                @Override
                public double calculatePrice() {
                    return wrappedPurchase.calculatePrice() * 0.98;
                }
            };
        }

        Purchase discountedPurchase = purchase;

        // Calculate final price after all applicable discounts
        double finalPrice = discountedPurchase.calculatePrice();
        System.out.println("Final price after all discounts: " + finalPrice);
    }
}
