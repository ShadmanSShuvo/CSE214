// factory method / simple factory pattern
// Shared interface across all communication channels
interface Notification {
    void notifyUser();
}

// Concrete notifications
class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS text alert.");
    }
}

class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an Email alert.");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a mobile push alert.");
    }
}

// Notification Factory handling runtime resolution
class NotificationFactory {
    public static Notification createNotification(String channel) {
        if (channel == null || channel.isEmpty()) {
            return null;
        }
        switch (channel.toUpperCase()) {
            case "SMS":
                return new SMSNotification();
            case "EMAIL":
                return new EmailNotification();
            case "PUSH":
                return new PushNotification();
            default:
                throw new IllegalArgumentException("Unknown channel variant: " + channel);
        }
    }
}

// Client application
public class MainB2 {
    public static void main(String[] args) {
        // Client only specifies the target string configuration
        Notification alert = NotificationFactory.createNotification("SMS");
        alert.notifyUser();

        Notification emailAlert = NotificationFactory.createNotification("Email");
        emailAlert.notifyUser();
    }
}