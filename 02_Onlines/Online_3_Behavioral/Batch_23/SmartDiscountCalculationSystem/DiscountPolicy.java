public interface DiscountPolicy {
    String getPolicyName();
    int calculateDiscountPercentage(PurchaseContext context);
}
