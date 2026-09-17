public class MobileAppNotificationChannel implements NotificationChannel {
    @Override
    public String getChannelName() {
        return "Mobile App Push";
    }

    @Override
    public void send(Notification notification, Customer customer) {
        System.out.println("--------------------------------------------------");
        System.out.println("[MOBILE APP PUSH NOTIFICATION]");
        System.out.println("Target Device ID: " + customer.getAppDeviceId());
        System.out.println("Header: " + notification.getTitle());
        System.out.println("Message: " + notification.getMessage());
        System.out.println("Payload: {type: \"" + notification.getType() + "\", timestamp: \"" + notification.getFormattedTimestamp() + "\"}");
        System.out.println("--------------------------------------------------");
    }
}
