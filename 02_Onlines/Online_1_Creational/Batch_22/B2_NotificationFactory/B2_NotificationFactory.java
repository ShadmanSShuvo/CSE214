// B2: Notification Library - Factory Method Pattern
// Task: Instantiate the correct Notification channel based on a string,
// so the client never needs to know concrete class names.

// ---------- Product Interface ----------
interface Notification {
    void notifyUser();
}

// ---------- Concrete Products ----------
class SMSNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an SMS notification.");
    }
}

class EmailNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending an Email notification.");
    }
}

class PushNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a Push notification.");
    }
}

// Example of future extension requiring minimal change:
class SlackMessageNotification implements Notification {
    @Override
    public void notifyUser() {
        System.out.println("Sending a Slack message notification.");
    }
}

// ---------- Creator (Factory) ----------
class NotificationFactory {
    public static Notification createNotification(String channel) {
        switch (channel) {
            case "SMS":
                return new SMSNotification();
            case "Email":
                return new EmailNotification();
            case "Push":
                return new PushNotification();
            case "SlackMessage":
                return new SlackMessageNotification();
            default:
                throw new IllegalArgumentException("Unknown channel: " + channel);
        }
    }
}

// ---------- Client ----------
public class B2_NotificationFactory {
    public static void main(String[] args) {
        Notification n1 = NotificationFactory.createNotification("SMS");
        n1.notifyUser();

        Notification n2 = NotificationFactory.createNotification("Email");
        n2.notifyUser();

        Notification n3 = NotificationFactory.createNotification("Push");
        n3.notifyUser();

        // Adding "SlackMessage" required only a new class + one switch case,
        // client code below is unchanged in structure.
        Notification n4 = NotificationFactory.createNotification("SlackMessage");
        n4.notifyUser();
    }
}
