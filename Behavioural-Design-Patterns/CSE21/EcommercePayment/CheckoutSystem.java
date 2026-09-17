public class CheckoutSystem {
    private PaymentStrategy paymentStrategy;

    public CheckoutSystem() {
    }

    public CheckoutSystem(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        System.out.println("[Checkout] Switched payment method to: " + paymentStrategy.getMethodName());
        this.paymentStrategy = paymentStrategy;
    }

    public PaymentStrategy getPaymentStrategy() {
        return paymentStrategy;
    }

    public boolean checkout(double orderTotal) {
        if (paymentStrategy == null) {
            System.out.println("[Error] No payment method selected! Please select a payment method before checkout.");
            return false;
        }

        System.out.println("==================================================");
        System.out.printf("Initiating checkout for total: $%.2f\n", orderTotal);
        System.out.println("Active Payment Method: " + paymentStrategy.getMethodName());
        boolean success = paymentStrategy.processPayment(orderTotal);
        if (success) {
            System.out.println("[Success] Checkout completed successfully! Receipt generated.");
        } else {
            System.out.println("[Failed] Payment failed. Please try another payment method.");
        }
        System.out.println("==================================================");
        return success;
    }
}
