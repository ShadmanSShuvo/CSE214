// ======================================================
// Component
// ======================================================
interface Notification {
    void send(String title, String message);
}

// ======================================================
// Concrete Components
// ======================================================
class EmailNotification implements Notification {

    @Override
    public void send(String title, String message) {
        System.out.println("Sending EMAIL");
        System.out.println("Title   : " + title);
        System.out.println("Message : " + message);
    }
}

class SMSNotification implements Notification {

    @Override
    public void send(String title, String message) {
        System.out.println("Sending SMS");
        System.out.println("Title   : " + title);
        System.out.println("Message : " + message);
    }
}

// ======================================================
// Base Decorator
// ======================================================
abstract class NotificationDecorator implements Notification {

    protected Notification notification;

    public NotificationDecorator(Notification notification) {
        this.notification = notification;
    }

    @Override
    public void send(String title, String message) {
        notification.send(title, message);
    }
}

// ======================================================
// Concrete Decorators
// ======================================================

class EncryptionDecorator extends NotificationDecorator {

    public EncryptionDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String title, String message) {

        System.out.println("[Encryption] Encrypting payload...");

        String encrypted = new StringBuilder(message).reverse().toString();

        notification.send(title, encrypted);

        System.out.println("[Encryption] Transmission complete.");
    }
}

class CompressionDecorator extends NotificationDecorator {

    public CompressionDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String title, String message) {

        System.out.println("[Compression] Compressing payload...");

        String compressed = message.replaceAll("[aeiouAEIOU]", "");

        notification.send(title, compressed);

        System.out.println("[Compression] Payload compressed.");
    }
}

class SignatureDecorator extends NotificationDecorator {

    public SignatureDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String title, String message) {

        System.out.println("[Signature] Attaching digital signature...");

        notification.send(title,
                message + "\n\n-- Signed by Enterprise PKI");

        System.out.println("[Signature] Signature verified.");
    }
}

class LoggingDecorator extends NotificationDecorator {

    public LoggingDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String title, String message) {

        long start = System.currentTimeMillis();

        System.out.println("[Log] Sending notification...");

        notification.send(title, message);

        long end = System.currentTimeMillis();

        System.out.println("[Log] Finished in "
                + (end - start)
                + " ms");
    }
}

class RetryDecorator extends NotificationDecorator {

    private final int maxAttempts;

    public RetryDecorator(Notification notification, int maxAttempts) {
        super(notification);
        this.maxAttempts = maxAttempts;
    }

    @Override
    public void send(String title, String message) {

        for (int i = 1; i <= maxAttempts; i++) {

            System.out.println("[Retry] Attempt " + i);

            try {

                notification.send(title, message);

                System.out.println("[Retry] Success");

                return;

            } catch (Exception e) {

                System.out.println("[Retry] Failed");
            }
        }

        System.out.println("[Retry] Giving up.");
    }
}

class MetricsDecorator extends NotificationDecorator {

    private static int totalNotifications = 0;

    public MetricsDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public void send(String title, String message) {

        totalNotifications++;

        notification.send(title, message);

        System.out.println(
                "[Metrics] Total notifications sent = "
                        + totalNotifications);
    }
}

// ======================================================
// Client
// ======================================================
public class DecoratorComplexDemo {

    public static void main(String[] args) {

        System.out.println("\n==========================");
        System.out.println("Example 1");
        System.out.println("==========================");

        Notification email = new EmailNotification();

        email.send(
                "Meeting",
                "Project meeting at 3 PM.");

        System.out.println("\n==========================");
        System.out.println("Example 2");
        System.out.println("==========================");

        Notification secureEmail = new EncryptionDecorator(
                new EmailNotification());

        secureEmail.send(
                "Payroll",
                "Salary credited.");

        System.out.println("\n==========================");
        System.out.println("Example 3");
        System.out.println("==========================");

        Notification compressedEncrypted = new CompressionDecorator(
                new EncryptionDecorator(
                        new EmailNotification()));

        compressedEncrypted.send(
                "Backup",
                "Nightly database backup completed successfully.");

        System.out.println("\n==========================");
        System.out.println("Example 4");
        System.out.println("==========================");

        Notification enterprisePipeline = new LoggingDecorator(
                new RetryDecorator(
                        new MetricsDecorator(
                                new SignatureDecorator(
                                        new CompressionDecorator(
                                                new EncryptionDecorator(
                                                        new EmailNotification())))),
                        3));

        enterprisePipeline.send(
                "Security Alert",
                "Unauthorized login detected from new device.");

        System.out.println("\n==========================");
        System.out.println("Example 5");
        System.out.println("==========================");

        Notification smsPipeline = new LoggingDecorator(
                new EncryptionDecorator(
                        new SMSNotification()));

        smsPipeline.send(
                "OTP",
                "Your OTP is 482931.");
    }
}
