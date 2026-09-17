import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BankingPlatform {
    private final String platformName;
    private final Map<String, Customer> customers;

    public BankingPlatform(String platformName) {
        this.platformName = platformName;
        this.customers = new HashMap<>();
    }

    public void registerCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
        System.out.println("[" + platformName + "] Registered customer: " + customer.getName() + " with default channel: " + customer.getPreferredChannel().getChannelName());
    }

    public void sendTransactionAlert(String customerId, String type, double amount, String account) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("Customer not found: " + customerId);
            return;
        }

        String title = "Transaction Alert: " + type;
        String message = String.format("A %s of $%.2f was processed on account ending in %s.", type, amount, account);
        Notification notification = new Notification(UUID.randomUUID().toString(), NotificationType.TRANSACTION_UPDATE, title, message);
        customer.receiveNotification(notification);
    }

    public void sendLowBalanceWarning(String customerId, double currentBalance, double threshold) {
        Customer customer = customers.get(customerId);
        if (customer == null) return;

        String title = "Low Balance Warning";
        String message = String.format("Your balance of $%.2f has dropped below the threshold of $%.2f. Please deposit funds.", currentBalance, threshold);
        Notification notification = new Notification(UUID.randomUUID().toString(), NotificationType.LOW_BALANCE_WARNING, title, message);
        customer.receiveNotification(notification);
    }

    public void sendPromotionalOffer(String customerId, String promoTitle, String promoDetails) {
        Customer customer = customers.get(customerId);
        if (customer == null) return;

        Notification notification = new Notification(UUID.randomUUID().toString(), NotificationType.PROMOTIONAL_OFFER, promoTitle, promoDetails);
        customer.receiveNotification(notification);
    }
}
