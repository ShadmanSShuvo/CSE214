public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  BANKING NOTIFICATION SYSTEM (STRATEGY PATTERN)  ");
        System.out.println("==================================================");

        BankingPlatform bank = new BankingPlatform("GlobalTrust Bank");

        // Create notification channels (Strategies)
        NotificationChannel emailChannel = new EmailNotificationChannel();
        NotificationChannel smsChannel = new SmsNotificationChannel();
        NotificationChannel mobileAppChannel = new MobileAppNotificationChannel();
        NotificationChannel whatsAppChannel = new WhatsAppNotificationChannel();

        // Register customer with initial strategy (Email)
        Customer customer = new Customer(
                "CUST1001",
                "Jane Doe",
                "jane.doe@example.com",
                "+1-555-0199",
                "DEV-PIXEL-8-ABC",
                emailChannel
        );
        bank.registerCustomer(customer);

        // 1. Send alert via Email
        System.out.println("\n--- Scenario 1: Initial Dispatch via Email ---");
        bank.sendTransactionAlert("CUST1001", "Deposit", 1250.00, "4321");

        // 2. Dynamically switch preferred channel to SMS
        System.out.println("\n--- Scenario 2: Dynamic Channel Switch to SMS ---");
        customer.setPreferredChannel(smsChannel);
        bank.sendLowBalanceWarning("CUST1001", 45.20, 100.00);

        // 3. Dynamically switch preferred channel to Mobile App
        System.out.println("\n--- Scenario 3: Dynamic Channel Switch to Mobile App ---");
        customer.setPreferredChannel(mobileAppChannel);
        bank.sendPromotionalOffer(
                "CUST1001",
                "Exclusive Cashback Offer",
                "Get 10% cashback on online grocery shopping this weekend with your debit card!"
        );

        // 4. Extensibility: Switch to newly introduced WhatsApp Channel without altering bank core
        System.out.println("\n--- Scenario 4: Extensibility with WhatsApp Channel ---");
        customer.setPreferredChannel(whatsAppChannel);
        bank.sendTransactionAlert("CUST1001", "Withdrawal", 200.00, "4321");

        System.out.println("\n==================================================");
        System.out.println("  Demonstration completed successfully.           ");
        System.out.println("==================================================");
    }
}
