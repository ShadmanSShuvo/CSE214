
interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Email: " + message);
    }
}

@SuppressWarnings("unused")
class SMSNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("SMS: " + message);
    }
}

@SuppressWarnings("unused")
class PushNotifier implements Notifier {
    @Override
    public void send(String message) {
        System.out.println("Push: " + message);
    }
}

abstract class NotifierDecorator implements Notifier {
    protected Notifier wrappee;

    NotifierDecorator(Notifier wrappee) {
        this.wrappee = wrappee;
    }
}

class EncryptionDecorator extends NotifierDecorator {
    EncryptionDecorator(Notifier w) {
        super(w);
    }

    @Override
    public void send(String message) {
        String encrypted = "[ENCRYPTED]" + message;
        wrappee.send(encrypted);
    }
}

class PriorityDecorator extends NotifierDecorator {
    PriorityDecorator(Notifier w) {
        super(w);
    }

    @Override
    public void send(String message) {
        wrappee.send("[HIGH PRIORITY] " + message);
    }
}

class LoggingDecorator extends NotifierDecorator {
    LoggingDecorator(Notifier w) {
        super(w);
    }

    @Override
    public void send(String message) {
        wrappee.send(message);
        System.out.println("Log: notification sent - " + message);
    }
}

// Usage
public class A1Decorator {
    public static void main(String[] args) {
        Notifier notifier = new LoggingDecorator(
                new PriorityDecorator(
                        new EncryptionDecorator(
                                new EmailNotifier())));
        notifier.send("Motion detected at front door");
    }
}
