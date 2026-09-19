/**
 * Refined Abstraction in the Bridge Pattern.
 * Regular Tier: Earns 1 loyalty point for every £100 spent.
 */
public class RegularTier extends MembershipTier {
    public RegularTier(RedemptionMethod redemptionMethod) {
        super(redemptionMethod);
    }

    @Override
    public String getTierName() {
        return "Regular";
    }

    @Override
    public int getPointsPer100Spent() {
        return 1;
    }
}
