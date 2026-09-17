public class PaymentMethodDiscountPolicy implements DiscountPolicy {
    @Override
    public String getPolicyName() {
        return "Payment Method Discount";
    }

    @Override
    public int calculateDiscountPercentage(PurchaseContext context) {
        if (context.getPaymentMethod() == PaymentMethod.CASH) {
            return 8;
        } else if (context.getPaymentMethod() == PaymentMethod.MFS) {
            return 5;
        } else if (context.getPaymentMethod() == PaymentMethod.CARD) {
            return 2;
        }
        return 0;
    }
}
