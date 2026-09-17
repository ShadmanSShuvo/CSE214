public class BKashPayment implements PaymentStrategy {
    private final String mobileNumber;
    private final String pin;

    public BKashPayment(String mobileNumber, String pin) {
        this.mobileNumber = mobileNumber;
        this.pin = pin;
    }

    @Override
    public String getMethodName() {
        return "bKash (" + mobileNumber + ")";
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment Processing - bKash MFS]");
        System.out.println("Wallet Number: " + mobileNumber);
        System.out.println("Verifying PIN & sending OTP...");
        System.out.println("OTP verification successful.");
        System.out.printf("Successfully deducted ৳%.2f from bKash account.\n", amount);
        return true;
    }
}
