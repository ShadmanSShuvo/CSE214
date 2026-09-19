/**
 * Main demonstration for CSE-214 Online 2 (C2) - Structural Design Patterns.
 * Demonstrates:
 * 1. Bridge Pattern: Decoupling MembershipTier abstraction from
 * RedemptionMethod implementor.
 * 2. Point calculation rules:
 * - Regular: 1 point / £100 spent
 * - Premium: 2 points / £100 spent
 * - Gold: 3 points / £100 spent
 * 3. Redemption channels:
 * - Cashback (instant discount on current bill)
 * - Coupon (shopping voucher for future purchases)
 * - Charity Donation (donates monetary value to company charity)
 * 4. Any tier supporting any redemption method with dynamic switching.
 * 5. Independent extensibility:
 * - New tier: Platinum (5 points / £100 spent)
 * - New redemption method: Partner Airline Miles
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("        CSE-214 ONLINE 2 (C2) - CUSTOMER LOYALTY PROGRAMME (BRIDGE)             ");
        System.out.println("================================================================================\n");

        // 1. Instantiating Redemption Implementors
        RedemptionMethod cashback = new CashbackRedemption();
        RedemptionMethod coupon = new CouponRedemption();
        RedemptionMethod charity = new CharityDonationRedemption();

        // 2. Scenario 1: Regular Member with Coupon Redemption
        System.out.println("--- Scenario 1: Regular Member with Coupon Redemption ---");
        Customer alice = new Customer("Alice Walker", new RegularTier(coupon));
        alice.makePurchase(350.00); // 3 * £100 = 3 points
        alice.makePurchase(200.00); // 2 * £100 = 2 points (Total: 5 points)
        alice.redeemPoints(5, 0.00);
        System.out.println();

        // 3. Scenario 2: Premium Member with Charity Donation
        System.out.println("--- Scenario 2: Premium Member with Charity Donation ---");
        Customer bob = new Customer("Bob Davies", new PremiumTier(charity));
        bob.makePurchase(500.00); // 5 * 2 = 10 points
        bob.redeemPoints(10, 0.00);
        System.out.println();

        // 4. Scenario 3: Gold Member with Instant Cashback
        System.out.println("--- Scenario 3: Gold Member with Instant Cashback ---");
        Customer charlie = new Customer("Charlie Evans", new GoldTier(cashback));
        charlie.makePurchase(1200.00); // 12 * 3 = 36 points
        System.out.println("Charlie makes a new purchase of £150 and uses 20 points for instant discount:");
        charlie.redeemPoints(20, 150.00); // £20 discount on £150 bill
        System.out.println();

        // 5. Scenario 4: Dynamic Switching of Redemption Method at Runtime
        System.out.println("--- Scenario 4: Dynamic Switching of Redemption Method at Runtime ---");
        System.out.printf("Charlie currently has %d points remaining.%n", charlie.getLoyaltyPoints());
        charlie.setRedemptionPreference(charity);
        charlie.redeemPoints(10, 0.00);
        System.out.println();

        // 6. Scenario 5: Independent Extensibility Demonstration
        System.out.println("--- Scenario 5: Independent Extensibility (Bridge in Action) ---");
        System.out.println("A) Adding New Implementor [Airline Miles] without modifying Membership Tiers:");
        RedemptionMethod airlineMiles = new AirlineMilesRedemption();
        charlie.setRedemptionPreference(airlineMiles);
        charlie.redeemPoints(6, 0.00);
        System.out.println();

        System.out.println("B) Adding New Abstraction [Platinum Tier] without modifying Redemption Methods:");
        Customer diana = new Customer("Diana Prince", new PlatinumTier(cashback));
        diana.makePurchase(1000.00); // 10 * 5 = 50 points
        diana.redeemPoints(25, 80.00);
        System.out.println();

        System.out.println("C) Combining New Tier (Platinum) with New Redemption Method (Airline Miles):");
        diana.setRedemptionPreference(airlineMiles);
        diana.redeemPoints(25, 0.00);
        System.out.println();

        System.out.println("================================================================================");
        System.out.println("             All Bridge Pattern Scenarios Verified Successfully!                ");
        System.out.println("================================================================================");
    }
}
