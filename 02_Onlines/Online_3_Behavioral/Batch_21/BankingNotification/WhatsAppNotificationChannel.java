public class WhatsAppNotificationChannel implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "WhatsApp";
    }

    @Override
    public void send(Notification notification, Customer customer) {
        System.out.println("--------------------------------------------------");
        System.out.println("[WHATSAPP MESSAGE DISPATCHED]");
        System.out.println("WhatsApp ID: " + customer.getPhoneNumber());
        System.out.println("Template: Banking Alerts Bot");
        System.out.println("Message: *" + notification.getTitle() + "*\n" + notification.getMessage());
        System.out.println("Timestamp: " + notification.getFormattedTimestamp());
        System.out.println("--------------------------------------------------");
    }
}
