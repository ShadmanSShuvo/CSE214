/**
 * Concrete Implementor demonstrating extensibility in the Bridge Pattern.
 * Converts loyalty points into partner airline frequent flyer miles (e.g.,
 * British Airways Avios).
 * Demonstrates that new redemption methods can be added without changing any
 * MembershipTier classes.
 */
public class AirlineMilesRedemption implements RedemptionMethod {
    private static final int MILES_PER_POINT = 15;

    @Override
    public String getMethodName() {
        return "Partner Airline Miles";
    }

    @Override
    public void redeem(String customerName, int points, double monetaryValue, double currentBill) {
        int airlineMiles = points * MILES_PER_POINT;
        System.out.printf("  [Redemption: Airline Miles] Customer: %s%n", customerName);
        System.out.printf("    • Points Redeemed: %d points%n", points);
        System.out.printf("    • Converted to: %d Frequent Flyer Miles transferred to partner airline account%n",
                airlineMiles);
    }
}
