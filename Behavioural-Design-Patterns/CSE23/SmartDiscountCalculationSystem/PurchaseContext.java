public class PurchaseContext {
    private final double purchaseAmount;
    private final CustomerCategory customerCategory;
    private final PaymentMethod paymentMethod;

    public PurchaseContext(double purchaseAmount, CustomerCategory customerCategory, PaymentMethod paymentMethod) {
        this.purchaseAmount = purchaseAmount;
        this.customerCategory = customerCategory;
        this.paymentMethod = paymentMethod;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public CustomerCategory getCustomerCategory() {
        return customerCategory;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
}
