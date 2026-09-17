public class SmsNotificationChannel implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "SMS";
    }

    @Override
    public void send(Notification notification, Customer customer) {
        System.out.println("--------------------------------------------------");
        System.out.println("[SMS DISPATCHED]");
        System.out.println("Recipient Number: " + customer.getPhoneNumber());
        System.out.println("SMS Text: [" + notification.getType() + "] " + notification.getTitle() + " - " + notification.getMessage());
        System.out.println("Time: " + notification.getFormattedTimestamp());
        System.out.println("--------------------------------------------------");
    }
}
