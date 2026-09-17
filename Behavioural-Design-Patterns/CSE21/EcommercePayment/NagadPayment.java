public class NagadPayment implements PaymentStrategy {
    private final String mobileNumber;

    public NagadPayment(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String getMethodName() {
        return "Nagad MFS (" + mobileNumber + ")";
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment Processing - Nagad MFS]");
        System.out.println("Wallet Number: " + mobileNumber);
        System.out.println("Nagad instant checkout API called...");
        System.out.printf("Successfully processed ৳%.2f via Nagad.\n", amount);
        return true;
    }
}
