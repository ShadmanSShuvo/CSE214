public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;
    private final String cardHolderName;
    private final String cvv;
    private final String expiryDate;

    public CreditCardPayment(String cardNumber, String cardHolderName, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    @Override
    public String getMethodName() {
        return "Credit Card (" + maskCardNumber(cardNumber) + ")";
    }

    @Override
    public boolean processPayment(double amount) {
        System.out.println("[Payment Processing - Credit Card]");
        System.out.println("Card Holder: " + cardHolderName);
        System.out.println("Card: " + maskCardNumber(cardNumber) + " | Exp: " + expiryDate);
        System.out.println("Contacting Payment Gateway with CVV verification...");
        System.out.printf("Successfully charged $%.2f to credit card.\n", amount);
        return true;
    }

    private String maskCardNumber(String num) {
        if (num == null || num.length() < 4) return "****";
        return "****-****-****-" + num.substring(num.length() - 4);
    }
}
