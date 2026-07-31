import java.util.ArrayList;
import java.util.List;

/**
 * Observer interface (Observer Design Pattern).
 * Any citizen registered with an AlertCategory (Subject) implements this
 * interface so it can be notified automatically when a new alert is published.
 */
interface AlertObserver {
    void update(Alert alert);
    String getName();
}

/**
 * Concrete Observer.
 * A Citizen keeps a personal record of every notification it has received
 * so it can display its own notification history on request.
 */
class Citizen implements AlertObserver {
    private final String name;
    private final List<Alert> receivedNotifications = new ArrayList<>();

    public Citizen(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(Alert alert) {
        receivedNotifications.add(alert);
        System.out.println("  [Notify -> " + name + "] " + alert.getSummary());
    }

    public void displayNotifications() {
        System.out.println("Notifications received by " + name + ":");
        if (receivedNotifications.isEmpty()) {
            System.out.println("  (no notifications received yet)");
            return;
        }
        int i = 1;
        for (Alert a : receivedNotifications) {
            System.out.println("  " + (i++) + ". " + a.getSummary());
        }
    }
}
