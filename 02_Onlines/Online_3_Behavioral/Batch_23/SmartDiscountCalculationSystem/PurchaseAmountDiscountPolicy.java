public class PurchaseAmountDiscountPolicy implements DiscountPolicy {
    @Override
    public String getPolicyName() {
        return "Purchase Amount Discount";
    }

    @Override
    public int calculateDiscountPercentage(PurchaseContext context) {
        int completeThousands = (int) (context.getPurchaseAmount() / 1000.0);
        int percentage = completeThousands * 5;
        return Math.min(percentage, 25);
    }
}
