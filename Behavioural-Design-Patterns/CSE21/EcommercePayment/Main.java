public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  E-COMMERCE PAYMENT SYSTEM (STRATEGY PATTERN)   ");
        System.out.println("==================================================");

        CheckoutSystem checkout = new CheckoutSystem();

        // 1. Customer attempts checkout without selecting strategy
        System.out.println("\n--- Step 1: Checkout without selecting method ---");
        checkout.checkout(99.99);

        // 2. Select Credit Card
        System.out.println("\n--- Step 2: Select Credit Card Strategy ---");
        PaymentStrategy creditCard = new CreditCardPayment("4111222233334444", "John Doe", "123", "12/28");
        checkout.setPaymentStrategy(creditCard);
        checkout.checkout(250.00);

        // 3. Customer switches to bKash
        System.out.println("\n--- Step 3: Switch Strategy to bKash ---");
        PaymentStrategy bkash = new BKashPayment("01711000000", "54321");
        checkout.setPaymentStrategy(bkash);
        checkout.checkout(1500.00);

        // 4. Customer switches to Cryptocurrency (Bitcoin)
        System.out.println("\n--- Step 4: Switch Strategy to Cryptocurrency (Bitcoin) ---");
        PaymentStrategy bitcoin = new CryptoPayment("Bitcoin (BTC)", "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
        checkout.setPaymentStrategy(bitcoin);
        checkout.checkout(750.50);

        // 5. Extensibility: Add and switch to newly integrated Nagad payment
        System.out.println("\n--- Step 5: Extensibility - Switch to New Nagad Payment ---");
        PaymentStrategy nagad = new NagadPayment("01811223344");
        checkout.setPaymentStrategy(nagad);
        checkout.checkout(320.00);

        System.out.println("\nAll payment strategies executed and verified cleanly.");
    }
}
