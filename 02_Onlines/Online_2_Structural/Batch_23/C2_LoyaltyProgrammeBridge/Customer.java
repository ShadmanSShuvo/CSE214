/**
 * Represents a customer enrolled in the loyalty programme.
 * Encapsulates points balance, purchases, tier upgrades, and redemption
 * operations.
 */
public class Customer {
    private final String name;
    private MembershipTier tier;
    private int loyaltyPoints;

    public Customer(String name, MembershipTier tier) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty.");
        }
        if (tier == null) {
            throw new IllegalArgumentException("Customer must be assigned a membership tier.");
        }
        this.name = name.trim();
        this.tier = tier;
        this.loyaltyPoints = 0;
    }

    public String getName() {
        return name;
    }

    public MembershipTier getTier() {
        return tier;
    }

    public void setTier(MembershipTier tier) {
        if (tier == null) {
            throw new IllegalArgumentException("Membership tier cannot be null.");
        }
        System.out.printf("⬆️ TIER UPDATE: %s upgraded/changed tier to %s!%n", name, tier.getTierName());
        this.tier = tier;
    }

    public void setRedemptionPreference(RedemptionMethod method) {
        tier.setRedemptionMethod(method);
        System.out.printf("🔄 PREFERENCE UPDATE: %s switched redemption method to [%s].%n",
                name, method.getMethodName());
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void makePurchase(double amount) {
        int earned = tier.calculatePointsEarned(amount);
        loyaltyPoints += earned;
        System.out.printf("🛒 PURCHASE: %s (%s Tier) spent £%.2f -> Earned %d points! (New Balance: %d points)%n",
                name, tier.getTierName(), amount, earned, loyaltyPoints);
    }

    public boolean redeemPoints(int pointsToRedeem, double currentBill) {
        if (pointsToRedeem <= 0) {
            System.err.println("Points to redeem must be greater than 0.");
            return false;
        }
        if (pointsToRedeem > loyaltyPoints) {
            System.out.printf("❌ REDEMPTION FAILED: %s has only %d points, but tried to redeem %d points.%n",
                    name, loyaltyPoints, pointsToRedeem);
            return false;
        }

        System.out.printf("🎁 REDEEMING: %s is redeeming %d points via [%s]...%n",
                name, pointsToRedeem, tier.getRedemptionMethod().getMethodName());
        tier.executeRedemption(name, pointsToRedeem, currentBill);
        loyaltyPoints -= pointsToRedeem;
        System.out.printf("    Remaining Balance for %s: %d points.%n", name, loyaltyPoints);
        return true;
    }
}
