/**
 * Refined Abstraction demonstrating extensibility in the Bridge Pattern.
 * Platinum Tier: VIP tier earning 5 loyalty points for every £100 spent.
 * Demonstrates that new membership tiers can be added without modifying
 * existing redemption methods.
 */
public class PlatinumTier extends MembershipTier {
    public PlatinumTier(RedemptionMethod redemptionMethod) {
        super(redemptionMethod);
    }

    @Override
    public String getTierName() {
        return "Platinum (VIP)";
    }

    @Override
    public int getPointsPer100Spent() {
        return 5;
    }
}
