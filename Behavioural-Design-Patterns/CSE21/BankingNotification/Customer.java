public class Customer {
    private final String customerId;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String appDeviceId;
    private NotificationChannel preferredChannel;

    public Customer(String customerId, String name, String email, String phoneNumber, String appDeviceId, NotificationChannel preferredChannel) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.appDeviceId = appDeviceId;
        this.preferredChannel = preferredChannel;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAppDeviceId() {
        return appDeviceId;
    }

    public NotificationChannel getPreferredChannel() {
        return preferredChannel;
    }

    public void setPreferredChannel(NotificationChannel preferredChannel) {
        System.out.println("[Channel Switched] " + name + " switched preferred channel to: " + preferredChannel.getChannelName());
        this.preferredChannel = preferredChannel;
    }

    public void receiveNotification(Notification notification) {
        if (preferredChannel == null) {
            System.out.println("No notification channel configured for customer " + name);
            return;
        }
        preferredChannel.send(notification, this);
    }
}
