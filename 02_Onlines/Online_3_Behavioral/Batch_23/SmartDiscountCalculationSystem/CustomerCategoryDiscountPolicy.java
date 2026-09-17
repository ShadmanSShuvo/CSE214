public class CustomerCategoryDiscountPolicy implements DiscountPolicy {
    @Override
    public String getPolicyName() {
        return "Customer Category Discount";
    }

    @Override
    public int calculateDiscountPercentage(PurchaseContext context) {
        if (context.getCustomerCategory() == CustomerCategory.PREMIUM) {
            return 15;
        } else if (context.getCustomerCategory() == CustomerCategory.REGULAR) {
            return 5;
        }
        return 0;
    }
}
