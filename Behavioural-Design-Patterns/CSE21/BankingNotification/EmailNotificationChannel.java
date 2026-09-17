public class EmailNotificationChannel implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "Email";
    }

    @Override
    public void send(Notification notification, Customer customer) {
        System.out.println("--------------------------------------------------");
        System.out.println("[EMAIL DISPATCHED]");
        System.out.println("To: " + customer.getName() + " <" + customer.getEmail() + ">");
        System.out.println("Subject: [" + notification.getType() + "] " + notification.getTitle());
        System.out.println("Body: " + notification.getMessage());
        System.out.println("Sent at: " + notification.getFormattedTimestamp());
        System.out.println("Security Note: If you did not authorize this, contact bank support.");
        System.out.println("--------------------------------------------------");
    }
}
