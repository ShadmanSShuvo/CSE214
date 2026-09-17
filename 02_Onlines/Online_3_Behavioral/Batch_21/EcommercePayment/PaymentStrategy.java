public interface PaymentStrategy {
    String getMethodName();
    boolean processPayment(double amount);
}
