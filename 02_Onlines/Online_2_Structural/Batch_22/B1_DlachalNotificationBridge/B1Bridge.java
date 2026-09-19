
// Implementor
interface MessageSender {
    void sendMessage(String content);
}

class EmailSender implements MessageSender {
    public void sendMessage(String content) {
        System.out.println("Email -> " + content);
    }
}

class SMSSender implements MessageSender {
    public void sendMessage(String content) {
        System.out.println("SMS -> " + content);
    }
}

class WhatsAppSender implements MessageSender {
    public void sendMessage(String content) {
        System.out.println("WhatsApp -> " + content);
    }
}

// Abstraction
abstract class Notification {
    protected MessageSender sender;

    Notification(MessageSender sender) {
        this.sender = sender;
    }

    abstract void notifyUser(String userInfo);
}

class BazarConfirmedNotification extends Notification {
    BazarConfirmedNotification(MessageSender sender) {
        super(sender);
    }

    void notifyUser(String userInfo) {
        sender.sendMessage("Hi " + userInfo + ", your bazar has been confirmed!");
    }
}

class BazarDispatchedNotification extends Notification {
    BazarDispatchedNotification(MessageSender sender) {
        super(sender);
    }

    void notifyUser(String userInfo) {
        sender.sendMessage("Hi " + userInfo + ", your bazar is on the way!");
    }
}

class PaymentFailedNotification extends Notification {
    PaymentFailedNotification(MessageSender sender) {
        super(sender);
    }

    void notifyUser(String userInfo) {
        sender.sendMessage("Hi " + userInfo + ", your payment failed. Please retry.");
    }
}

// Usage
public class B1Bridge {
    public static void main(String[] args) {
        Notification n1 = new BazarDispatchedNotification(new WhatsAppSender());
        n1.notifyUser("Rafi");

        Notification n2 = new PaymentFailedNotification(new SMSSender());
        n2.notifyUser("Nadia");
        // Adding a new channel (e.g., PushSender) or new event type
        // requires no changes to the other dimension.
    }
}
