/**
 * Refined Abstraction in the Bridge Pattern.
 * Gold Tier: Earns 3 loyalty points for every £100 spent.
 */
public class GoldTier extends MembershipTier {
    public GoldTier(RedemptionMethod redemptionMethod) {
        super(redemptionMethod);
    }

    @Override
    public String getTierName() {
        return "Gold";
    }

    @Override
    public int getPointsPer100Spent() {
        return 3;
    }
}
