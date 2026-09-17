// ==========================================================
// Bridge Pattern Demo
// Example: Smart Home Notification System
//
// Two Degrees of Freedom:
// 1. Notification Type (Abstraction)
// 2. Delivery Channel (Implementation)
//
// Compile & Run:
// javac BridgeDemo.java
// java BridgeDemo
// ==========================================================

// -------------------------------
// Implementation Interface
// -------------------------------
interface Channel {
    void deliver(String title, String message, int priority);
}

// -------------------------------
// Concrete Implementations
// -------------------------------

class EmailChannel implements Channel {

    @Override
    public void deliver(String title, String message, int priority) {
        System.out.println("\n========== EMAIL ==========");
        System.out.println("Title    : " + title);
        System.out.println("Message  : " + message);
        System.out.println("Priority : " + priority);
    }
}

class SMSChannel implements Channel {

    @Override
    public void deliver(String title, String message, int priority) {
        System.out.println("\n========== SMS ==========");
        System.out.println("Title    : " + title);
        System.out.println("Message  : " + message);
        System.out.println("Priority : " + priority);
    }
}

class PushChannel implements Channel {

    @Override
    public void deliver(String title, String message, int priority) {
        System.out.println("\n========== PUSH ==========");
        System.out.println("Title    : " + title);
        System.out.println("Message  : " + message);
        System.out.println("Priority : " + priority);
    }
}

class SlackChannel implements Channel {

    @Override
    public void deliver(String title, String message, int priority) {
        System.out.println("\n========== SLACK ==========");
        System.out.println("Title    : " + title);
        System.out.println("Message  : " + message);
        System.out.println("Priority : " + priority);
    }
}

// -------------------------------
// Abstraction
// -------------------------------

abstract class Notification {

    protected Channel channel;

    public Notification(Channel channel) {
        this.channel = channel;
    }

    public abstract void send();
}

// -------------------------------
// Refined Abstractions
// -------------------------------

class SecurityAlert extends Notification {

    public SecurityAlert(Channel channel) {
        super(channel);
    }

    @Override
    public void send() {
        channel.deliver(
                "Security Alert",
                "Motion detected at Front Door.",
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
                "Today's energy consumption is 18.2 kWh.",
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
                "John Doe has arrived at the main gate.",
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
                "Air filter replacement is due tomorrow.",
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
                "Emergency",
                "Fire detected in Kitchen!",
                10);
    }
}

// -------------------------------
// Client
// -------------------------------

public class BridgeComplexDemo {

    public static void main(String[] args) {

        Notification n1 = new SecurityAlert(new EmailChannel());

        Notification n2 = new SecurityAlert(new SMSChannel());

        Notification n3 = new SecurityAlert(new SlackChannel());

        Notification n4 = new EnergyReport(new PushChannel());

        Notification n5 = new VisitorNotification(new EmailChannel());

        Notification n6 = new MaintenanceReminder(new SMSChannel());

        Notification n7 = new EmergencyAlert(new PushChannel());

        Notification[] notifications = {
                n1, n2, n3, n4, n5, n6, n7
        };

        for (Notification notification : notifications) {
            notification.send();
        }
    }
}

/*
 * ==============================================================
 *
 * WHY BRIDGE?
 *
 * Two independent dimensions of variation:
 *
 * 1) Notification Types
 * ---------------------
 * SecurityAlert
 * EnergyReport
 * VisitorNotification
 * MaintenanceReminder
 * EmergencyAlert
 *
 * 2) Delivery Channels
 * --------------------
 * EmailChannel
 * SMSChannel
 * PushChannel
 * SlackChannel
 *
 * Every Notification can work with every Channel.
 *
 * Examples:
 *
 * new SecurityAlert(new EmailChannel());
 *
 * new SecurityAlert(new SMSChannel());
 *
 * new EnergyReport(new PushChannel());
 *
 * new VisitorNotification(new SlackChannel());
 *
 * new EmergencyAlert(new EmailChannel());
 *
 * ==============================================================
 *
 * Without Bridge:
 *
 * EmailSecurityAlert
 * SMSSecurityAlert
 * PushSecurityAlert
 * SlackSecurityAlert
 *
 * EmailEnergyReport
 * SMSEnergyReport
 * PushEnergyReport
 * SlackEnergyReport
 *
 * ...
 *
 * If there are:
 *
 * 5 notification types
 * 4 channels
 *
 * Need:
 *
 * 5 × 4 = 20 classes
 *
 * ==============================================================
 *
 * With Bridge:
 *
 * Notification hierarchy:
 * Notification
 * ├── SecurityAlert
 * ├── EnergyReport
 * ├── VisitorNotification
 * ├── MaintenanceReminder
 * └── EmergencyAlert
 *
 * Channel hierarchy:
 * Channel
 * ├── EmailChannel
 * ├── SMSChannel
 * ├── PushChannel
 * └── SlackChannel
 *
 * Total classes:
 *
 * 1 + 5 + 1 + 4 = 11 classes
 *
 * Instead of 20 specialized subclasses.
 *
 * ==============================================================
 */
