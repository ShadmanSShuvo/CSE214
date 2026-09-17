public interface NotificationChannel {
    String getChannelName();
    void send(Notification notification, Customer customer);
}
