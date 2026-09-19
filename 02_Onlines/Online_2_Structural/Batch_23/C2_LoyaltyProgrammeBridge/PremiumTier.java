/**
 * Refined Abstraction in the Bridge Pattern.
 * Premium Tier: Earns 2 loyalty points for every £100 spent.
 */
public class PremiumTier extends MembershipTier {
    public PremiumTier(RedemptionMethod redemptionMethod) {
        super(redemptionMethod);
    }

    @Override
    public String getTierName() {
        return "Premium";
    }

    @Override
    public int getPointsPer100Spent() {
        return 2;
    }
}
