/**
 * Concrete Implementor in Bridge Pattern.
 * Donates the monetary value of the redeemed points to the company's charity
 * programme.
 */
public class CharityDonationRedemption implements RedemptionMethod {
    private static int receiptCounter = 5001;

    @Override
    public String getMethodName() {
        return "Charity Donation";
    }

    @Override
    public void redeem(String customerName, int points, double monetaryValue, double currentBill) {
        String receiptNumber = "REC-CHARITY-" + (receiptCounter++);
        System.out.printf("  [Redemption: Charity Donation] Customer: %s%n", customerName);
        System.out.printf("    • Points Redeemed: %d points%n", points);
        System.out.printf("    • Donated Amount: £%.2f to the Company Children's Welfare Foundation%n", monetaryValue);
        System.out.printf("    • Tax Receipt Generated: %s (Thank you for your generosity!)%n", receiptNumber);
    }
}
