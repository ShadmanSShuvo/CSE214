// ==========================================================
// Bridge Pattern Demo
// Example: Smart Home Notification System
//
// Two Degrees of Freedom:
// 1. Notification Type (Abstraction)
// 2. Delivery Channel (Implementation)
//
// Compile:
// javac BridgeDemo.java
//
// Run:
// java BridgeDemo
// ==========================================================

// ==========================================================
// Implementation Hierarchy
// ==========================================================

interface Channel {
    void deliver(String title, String message, int priority);
}

// ---------------------------
// Simulated Services
// ---------------------------

class EmailService {

    public void sendEmail(String subject, String body) {
        System.out.println("\n[EMAIL SERVICE]");
        System.out.println("Sending Email...");
        System.out.println("Subject : " + subject);
        System.out.println("Body    : " + body);
    }
}

class SMSGateway {

    public void sendSMS(String message) {
        System.out.println("\n[SMS GATEWAY]");
        System.out.println("Sending SMS...");
        System.out.println("Text : " + message);
    }
}

class PushService {

    public void sendPush(String title, String body) {
        System.out.println("\n[PUSH SERVICE]");
        System.out.println("Sending Push Notification...");
        System.out.println("Title : " + title);
        System.out.println("Body  : " + body);
    }
}

class SlackAPI {

    public void postMessage(String channel, String message) {
        System.out.println("\n[SLACK API]");
        System.out.println("Posting to " + channel);
        System.out.println("Message : " + message);
    }
}

// ==========================================================
// Concrete Implementations
// ==========================================================

class EmailChannel implements Channel {

    private EmailService emailService = new EmailService();

    @Override
    public void deliver(String title, String message, int priority) {

        emailService.sendEmail(
                "[Priority " + priority + "] " + title,
                message);
    }
}

class SMSChannel implements Channel {

    private SMSGateway smsGateway = new SMSGateway();

    @Override
    public void deliver(String title, String message, int priority) {

        smsGateway.sendSMS(
                title + " : " + message);
    }
}

class PushChannel implements Channel {

    private PushService pushService = new PushService();

    @Override
    public void deliver(String title, String message, int priority) {

        pushService.sendPush(
                title,
                message);
    }
}

class SlackChannel implements Channel {

    private SlackAPI slackAPI = new SlackAPI();

    @Override
    public void deliver(String title, String message, int priority) {

        slackAPI.postMessage(
                "#smart-home-alerts",
                "[" + priority + "] " + title + " -> " + message);
    }
}

// ==========================================================
// Abstraction
// ==========================================================

abstract class Notification {

    protected Channel channel;

    public Notification(Channel channel) {
        this.channel = channel;
    }

    public abstract void send();
}

// ==========================================================
// Refined Abstractions
// ==========================================================

class SecurityAlert extends Notification {

    public SecurityAlert(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {

        channel.deliver(
                "Security Alert",
                "Motion detected at the Front Door.",
                10);
    }
}

class EnergyReport extends Notification {

    public EnergyReport(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {

        channel.deliver(
                "Daily Energy Report",
                "Today's energy usage is 18.2 kWh.",
                3);
    }
}

class VisitorNotification extends Notification {

    public VisitorNotification(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {

        channel.deliver(
                "Visitor Arrived",
                "John Doe is waiting at the main gate.",
                5);
    }
}

class MaintenanceReminder extends Notification {

    public MaintenanceReminder(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {

        channel.deliver(
                "Maintenance Reminder",
                "Air conditioner filter replacement is due tomorrow.",
                4);
    }
}

class EmergencyAlert extends Notification {

    public EmergencyAlert(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {

        channel.deliver(
                "Emergency Alert",
                "Fire detected in the Kitchen!",
                10);
    }
}

// ==========================================================
// Client
// ==========================================================

public class BridgeComplexOptimized {

    public static void main(String[] args) {

        Notification[] notifications = {

                new SecurityAlert(new EmailChannel()),

                new SecurityAlert(new SMSChannel()),

                new EnergyReport(new PushChannel()),

                new VisitorNotification(new SlackChannel()),

                new MaintenanceReminder(new EmailChannel()),

                new EmergencyAlert(new PushChannel())
        };

        for (Notification notification : notifications) {
            notification.send();
        }
    }
}
