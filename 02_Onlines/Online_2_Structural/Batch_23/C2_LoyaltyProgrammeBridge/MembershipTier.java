/**
 * Abstraction in the Bridge Pattern.
 * Decouples membership tiers from redemption methods.
 * Any membership tier can hold any redemption method and can switch dynamically
 * at runtime.
 */
public abstract class MembershipTier {
    protected RedemptionMethod redemptionMethod;

    public MembershipTier(RedemptionMethod redemptionMethod) {
        if (redemptionMethod == null) {
            throw new IllegalArgumentException("Redemption method cannot be null.");
        }
        this.redemptionMethod = redemptionMethod;
    }

    /**
     * Allows dynamic switching of redemption method at runtime.
     */
    public void setRedemptionMethod(RedemptionMethod redemptionMethod) {
        if (redemptionMethod == null) {
            throw new IllegalArgumentException("Redemption method cannot be null.");
        }
        this.redemptionMethod = redemptionMethod;
    }

    public RedemptionMethod getRedemptionMethod() {
        return redemptionMethod;
    }

    /**
     * @return Name of the membership tier.
     */
    public abstract String getTierName();

    /**
     * @return Number of points earned for every £100 spent.
     */
    public abstract int getPointsPer100Spent();

    /**
     * Calculates points earned for a purchase.
     * Earns points on every full £100 spent.
     *
     * @param amountSpent total purchase amount in GBP (£).
     * @return points earned.
     */
    public int calculatePointsEarned(double amountSpent) {
        if (amountSpent < 0) {
            return 0;
        }
        int hundreds = (int) (amountSpent / 100.0);
        return hundreds * getPointsPer100Spent();
    }

    /**
     * Calculates the monetary value of accumulated loyalty points.
     * Standard conversion rate: 1 point = £1.00.
     *
     * @param points number of loyalty points.
     * @return monetary value in GBP (£).
     */
    public double getMonetaryValue(int points) {
        return points * 1.0; // £1 per point
    }

    /**
     * Bridges to the implementor (RedemptionMethod) to process point redemption.
     */
    public void executeRedemption(String customerName, int points, double currentBill) {
        double monetaryValue = getMonetaryValue(points);
        redemptionMethod.redeem(customerName, points, monetaryValue, currentBill);
    }
}
