/**
 * Concrete Implementor in Bridge Pattern.
 * Converts loyalty points into an instant discount on the current purchase
 * bill.
 */
public class CashbackRedemption implements RedemptionMethod {
    @Override
    public String getMethodName() {
        return "Instant Cashback";
    }

    @Override
    public void redeem(String customerName, int points, double monetaryValue, double currentBill) {
        double discount = Math.min(monetaryValue, currentBill);
        double finalBill = Math.max(0.0, currentBill - discount);
        System.out.printf("  [Redemption: Cashback] Customer: %s%n", customerName);
        System.out.printf("    • Points Redeemed: %d points (Value: £%.2f)%n", points, monetaryValue);
        System.out.printf("    • Current Bill: £%.2f | Discount Applied: £%.2f%n", currentBill, discount);
        System.out.printf("    • Final Bill Due: £%.2f%n", finalBill);
    }
}
