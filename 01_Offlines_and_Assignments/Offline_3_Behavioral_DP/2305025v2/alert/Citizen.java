import java.util.ArrayList;
import java.util.List;

public class Citizen implements AlertObserver {
    private final String name;
    private final List<Alert> alerts = new ArrayList<>();

    public Citizen(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(Alert alert) {
        alerts.add(alert);
        System.out.println(name + " <- " + alert.getSummary());
    }

    public void showAlerts() {
        System.out.println("\nNotifications: " + name);

        if (alerts.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (int i = 0; i < alerts.size(); i++)
            System.out.println((i + 1) + ". " + alerts.get(i).getSummary());
    }
}
