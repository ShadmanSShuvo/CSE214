/**
 * Concrete Implementor in Bridge Pattern.
 * Converts loyalty points into shopping coupons for future purchases.
 */
public class CouponRedemption implements RedemptionMethod {
    private static int couponCounter = 1001;

    @Override
    public String getMethodName() {
        return "Shopping Coupon";
    }

    @Override
    public void redeem(String customerName, int points, double monetaryValue, double currentBill) {
        String couponCode = "CPN-LOYALTY-" + (couponCounter++);
        System.out.printf("  [Redemption: Shopping Coupon] Customer: %s%n", customerName);
        System.out.printf("    • Points Redeemed: %d points%n", points);
        System.out.printf("    • Issued Voucher: %s valued at £%.2f%n", couponCode, monetaryValue);
        System.out.printf("    • Terms: Valid for 6 months on any in-store or online future purchase.%n");
    }
}
